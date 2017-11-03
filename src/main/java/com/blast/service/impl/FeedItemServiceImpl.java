package com.blast.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blast.domain.FeedItem;
import com.blast.domain.StatusItem;
import com.blast.repository.FeedItemRepository;
import com.blast.repository.SocialUserConnectionRepository;
import com.blast.repository.StatusItemRepository;
import com.blast.service.FeedItemService;
import com.blast.service.KeywordService;
import com.blast.service.SocialFollowService;
import com.blast.service.UserService;
import com.blast.service.dto.FeedItemDTO;
import com.blast.service.dto.UserDTO;
import com.blast.service.mapper.FeedItemMapper;
import com.blast.service.util.BlastConstant;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import sun.misc.BASE64Decoder;


/**
 * Service Implementation for managing FeedItem.
 */
@Service
@Transactional
public class FeedItemServiceImpl implements FeedItemService{

    private final Logger log = LoggerFactory.getLogger(FeedItemServiceImpl.class);
    
    private final FeedItemRepository feedItemRepository;
    
    private final StatusItemRepository statusItemRepository;
    
    private final UserService userService;
    
    private final KeywordService keywordService;
    
    private final FeedItemMapper feedItemMapper;
    
    private final Environment env;
    
    private String storageProjectId;

    private String storageBucketName;

    private Storage storage;
    
    public FeedItemServiceImpl(FeedItemRepository feedItemRepository, FeedItemMapper feedItemMapper
    		, UserService userService, StatusItemRepository statusItemRepository, SocialFollowService socialFollowService
    		, SocialUserConnectionRepository socialUserConnectionRepository
    		, KeywordService keywordService
    		, Environment env) throws IOException {
        this.feedItemRepository = feedItemRepository;
        this.feedItemMapper = feedItemMapper;
        this.userService = userService;
        this.statusItemRepository = statusItemRepository;
        this.keywordService = keywordService;
        this.env = env;
        
        this.storageProjectId = this.env.getProperty("spring.google.storage.project-id");
        this.storageBucketName = this.env.getProperty("spring.google.storage.bucket-name");
        
		Resource resource = new ClassPathResource("config/blast-api.json");
		
		storage = StorageOptions.newBuilder()
				.setProjectId(storageProjectId)
			    .setCredentials(ServiceAccountCredentials.fromStream(resource.getInputStream()))
			    .build()
			    .getService();
    }

    /**
     * Save a feedItem.
     *
     * @param feedItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FeedItemDTO save(FeedItemDTO feedItemDTO) {
        log.debug("Request to save FeedItem : {}", feedItemDTO);
        FeedItem feedItem = feedItemMapper.toEntity(feedItemDTO);
        feedItem = feedItemRepository.save(feedItem);
        return feedItemMapper.toDto(feedItem);
    }

    /**
     *  Get all the feedItems.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FeedItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FeedItems");
        return feedItemRepository.findAll(pageable)
            .map(feedItemMapper::toDto);
    }

    /**
     *  Get one feedItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FeedItemDTO findOne(Long id) {
        log.debug("Request to get FeedItem : {}", id);
        FeedItem feedItem = feedItemRepository.findOne(id);
        return feedItemMapper.toDto(feedItem);
    }

    /**
     *  Delete the  feedItem by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FeedItem : {}", id);
        feedItemRepository.delete(id);
    }
    
    // hello world: aGVsbG8gd29ybGQ=
    @SuppressWarnings("unused")
	@Override
	public FeedItemDTO storeCloud(FeedItemDTO feedItemDTO) {
		try {
			com.blast.domain.User user = userService.getCurrentUser();

			// Upload
			processUploadToCloud(feedItemDTO);
			
			if (!isExistImage(feedItemDTO.getFilename()) || !isExistImage(feedItemDTO.getThumbFilename())) {
				log.error("Cannot upload image to cloud");
				return null;
			}
			
			// https://storage.googleapis.com/itsol-blast/test.txt
			// Store database
			// Find main keyword
			String mainKeyword = "";
			if (StringUtils.isNotEmpty(feedItemDTO.getKeywords())) {
				String[] tmp = feedItemDTO.getKeywords().split(",");
				mainKeyword = StringUtils.lowerCase(tmp[0]);
			}
			
			// Store feedItem
			FeedItem fItem = new FeedItem();
		    
			fItem.setImageUrl(feedItemDTO.getImageUrl());
			fItem.setKeywords(feedItemDTO.getKeywords());
			fItem.setShare(feedItemDTO.isShare());
			fItem.setUserId(user.getId());
			fItem.setFilename(feedItemDTO.getFilename());
			fItem.setMainKeyword(mainKeyword);
			fItem.setStatus(feedItemDTO.getStatus());
			fItem.setCreatedDate(new Date());
			fItem.setImageThumbUrl(feedItemDTO.getImageThumbUrl());
			FeedItem resultFeedItem = feedItemRepository.save(fItem);
			
			// Store like/hate
			if (feedItemDTO.getStatus() != null) {
				StatusItem statusItem = new StatusItem();
				statusItem.setItemId(resultFeedItem.getId());
				statusItem.setUserId(user.getId());
				statusItem.setStatus(feedItemDTO.getStatus());
				statusItemRepository.save(statusItem);
			}
			
			// Store main keyword
			if (StringUtils.isNotEmpty(mainKeyword)) {
				keywordService.updateStatus(mainKeyword, feedItemDTO.getStatus());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Reset data
		feedItemDTO.setData(null);
		
		return feedItemDTO;
	}
    
    private void processUploadToCloud(FeedItemDTO feedItemDTO) throws IOException {
    		Long currentTime = System.currentTimeMillis();
		
		// Create a bucket
		String filename = currentTime + "_" + feedItemDTO.getFilename();
		String thumbFilename = currentTime + "_thumb_" + feedItemDTO.getFilename();
		
		BlobId blobId = BlobId.of(storageBucketName, filename);
		
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(feedItemDTO.getContentType()).build();
		
		byte[] content = Base64.decodeBase64(feedItemDTO.getData());
		storage.create(blobInfo, content);
		storage.createAcl(blobId, Acl.of(User.ofAllUsers(), Role.READER));
		
		// Process resize to create thumb
		String thumbData = null;
		BufferedImage originBufImage = decodeToImage(feedItemDTO.getData());
		
		String imageUrl = "https://storage.googleapis.com/" + storageBucketName + "/" + filename;
		String imageThumbUrl = null; //"https://storage.googleapis.com/" + bucketName + "/" + thumbFilename;
		// Check size image
		if (originBufImage.getWidth() > BlastConstant.IMAGE_RESIZE_WIDTH 
				&& originBufImage.getHeight() > BlastConstant.IMAGE_RESIZE_HEIGHT) {
			thumbData = createThumbnailImage(originBufImage
					, BlastConstant.IMAGE_RESIZE_WIDTH, BlastConstant.IMAGE_RESIZE_HEIGHT, getFormatName(feedItemDTO.getContentType()));
			// Create thumb
			byte[] contentThumb = Base64.decodeBase64(thumbData);
			
			// Create thumb and upload
			BlobId blobIdThumb = BlobId.of(storageBucketName, thumbFilename);
			
			BlobInfo blobInfoThumb = BlobInfo.newBuilder(blobIdThumb).setContentType(feedItemDTO.getContentType()).build();
			
			storage.create(blobInfoThumb, contentThumb);
			storage.createAcl(blobIdThumb, Acl.of(User.ofAllUsers(), Role.READER));
			imageThumbUrl = "https://storage.googleapis.com/" + storageBucketName + "/" + thumbFilename;
		} else {
			imageThumbUrl = imageUrl;
			thumbFilename = filename;
		}
		
		// set thumb filename
		feedItemDTO.setFilename(filename);
		feedItemDTO.setThumbFilename(thumbFilename);
		
		feedItemDTO.setImageUrl(imageUrl);
		feedItemDTO.setImageThumbUrl(imageThumbUrl);
    }
    
    private String getFormatName(String contentType) {
    		return contentType.replace("image/", "");
    }
    
    private Boolean isExistImage(String name){

	    	try {
	//	      HttpURLConnection.setFollowRedirects(false);
	//	      // note : you may also need
	//	      //        HttpURLConnection.setInstanceFollowRedirects(false)
	//	      HttpURLConnection con =
	//	         (HttpURLConnection) new URL(url).openConnection();
	//	      con.setRequestMethod("HEAD");
	//	      
	//	      long lengthImage = con.getContentLengthLong();
	//	      if (lengthImage == 0) {
	//	    	  return false;
	//	      }
	//	      
	//	      return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
	    		BlobId blobId = BlobId.of(storageBucketName, name);
	    		Blob blob = storage.get(blobId);
	    		if (null == blob) {
	    			log.warn("Image file not exist, {}", name);
	    			return false;
	    		} 
	    		
	    		return true;
		} catch (Exception e) {
	       e.printStackTrace();
	       return false;
	    }
    }
    
    public BufferedImage dropAlphaChannel(BufferedImage src) {
        BufferedImage convertedImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
        convertedImg.getGraphics().drawImage(src, 0, 0, null);

        return convertedImg;
   }
    
    private String createThumbnailImage(BufferedImage bufferedImage, Integer width, Integer height, String formatName) {
    	String base64bytes = null;
    	try {
//	    	Mode mode = (double) width / (double) height >= (double) bufferedImage.getWidth() / (double) bufferedImage.getHeight() ? Scalr.Mode.FIT_TO_WIDTH
//	                : Scalr.Mode.FIT_TO_HEIGHT;
	    	Mode mode = (double) bufferedImage.getWidth() > (double) bufferedImage.getHeight() ? Scalr.Mode.FIT_TO_WIDTH
	                : Scalr.Mode.FIT_TO_HEIGHT;
	
	        bufferedImage = Scalr.resize(bufferedImage, Scalr.Method.AUTOMATIC, mode, width, height);
	
	        if (bufferedImage.getColorModel().hasAlpha()) {
	        	bufferedImage = dropAlphaChannel(bufferedImage);
        	}
//	        int x = 0;
//	        int y = 0;
//	
//	        if (mode == Scalr.Mode.FIT_TO_WIDTH) {
//	            y = (bufferedImage.getHeight() - height) / 2;
//	        } else if (mode == Scalr.Mode.FIT_TO_HEIGHT) {
//	            x = (bufferedImage.getWidth() - width) / 2;
//	        }
//	
//	        bufferedImage = Scalr.crop(bufferedImage, x, y, width, height);
	
	        
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        ImageIO.write(bufferedImage, formatName, out);
	        byte[] bytes = out.toByteArray();
	
	        base64bytes = Base64.encodeBase64String(bytes);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        return base64bytes;
    }
    
    public BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

	@Override
	public Page<FeedItemDTO> findAllByUserId(Pageable pageable) {
		com.blast.domain.User user = userService.getCurrentUser();
		Page<FeedItemDTO> data = feedItemRepository.findByUserId(user.getId(), pageable).map(feedItemMapper::toDto);;
		return data;
	}

	@Override
	public Page<FeedItemDTO> findAllByUserIdAndStatus(Pageable pageable, Integer status) {
		com.blast.domain.User user = userService.getCurrentUser();
		Page<FeedItemDTO> data = feedItemRepository.findByUserIdAndStatus(user.getId(), status, pageable).map(feedItemMapper::toDto);;
		return data;
	}

	@Override
	public Page<FeedItemDTO> findAllByTrendFriendAndStatus(Pageable pageable, Integer status) {
		// Get list following
		com.blast.domain.User user = userService.getCurrentUser();
		UserDTO userDTO = userService.getUserDTOWithDisplayName(user.getId());
		
		if (StringUtils.isEmpty(userDTO.getProviderId())) {
			return null;
		}
		List<Long> lstUserId = userService.getAllFollowingUserId(user.getId());
		
		// check
		if (lstUserId == null || lstUserId.size() == 0) {
			return null;
		}
		
		Page<FeedItemDTO> data = feedItemRepository.findAllByTrendFriendAndStatus(lstUserId, status, pageable).map(feedItemMapper::toDto);
		return data;
	}

	@Override
	public Page<FeedItemDTO> findAllByTrendAllAndStatus(Pageable pageable, Integer status) {
		Page<FeedItemDTO> data = feedItemRepository.findAllByTrendAllAndStatus(status, pageable).map(feedItemMapper::toDto);
		return data;
	}

	@Override
	public Page<FeedItemDTO> findAllByMainKeywordAndStatus(Pageable pageable, String keyword, Integer status) {
		Page<FeedItemDTO> data = feedItemRepository.findByMainKeywordAndStatus(keyword, status, pageable).map(feedItemMapper::toDto);
		return data;
	}

	@Override
	public FeedItemDTO findOneByMainKeyword(String keyword) {
		FeedItemDTO data = feedItemMapper.toDto(feedItemRepository.findFirstByMainKeywordOrderByCreatedDateDesc(keyword));
		return data;
	}

	@Override
	public FeedItemDTO findOneByMainKeywordFriend(List<Long> lstUserId, String keyword) {
		FeedItemDTO data = feedItemMapper.toDto(feedItemRepository.findFirstByMainKeywordFriend(lstUserId, keyword));
		return data;
	}
}

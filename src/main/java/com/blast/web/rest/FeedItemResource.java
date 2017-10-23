package com.blast.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.blast.service.FeedItemService;
import com.blast.service.KeywordService;
import com.blast.service.StatusItemService;
import com.blast.service.UserService;
import com.blast.web.rest.util.HeaderUtil;
import com.blast.web.rest.util.PaginationUtil;
import com.blast.service.dto.FeedItemDTO;
import com.blast.service.dto.KeywordDTO;
import com.blast.service.dto.StatusItemDTO;
import com.blast.service.dto.TrendStatusDTO;
import com.blast.service.dto.UserDTO;
import com.blast.service.dto.response.FeedItemsByKeywordAndStatusResponse;
import com.blast.service.util.BlastConstant;

import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FeedItem.
 */
@RestController
@RequestMapping("/api")
public class FeedItemResource {

    private final Logger log = LoggerFactory.getLogger(FeedItemResource.class);

    private static final String ENTITY_NAME = "feedItem";

    private final FeedItemService feedItemService;
    
    private final StatusItemService statusItemService;
    
    private final KeywordService keywordService;
    
    private final UserService userService;

    public FeedItemResource(FeedItemService feedItemService, StatusItemService statusItemService, UserService userService
    		, KeywordService keywordService) {
        this.feedItemService = feedItemService;
        this.statusItemService = statusItemService;
        this.userService = userService;
        this.keywordService = keywordService;
    }

    /**
     * POST  /feed-items : Create a new feedItem.
     *
     * @param feedItemDTO the feedItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new feedItemDTO, or with status 400 (Bad Request) if the feedItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/feed-items")
    @Timed
    public ResponseEntity<FeedItemDTO> createFeedItem(@RequestBody FeedItemDTO feedItemDTO) throws URISyntaxException {
        log.debug("REST request to save FeedItem : {}", feedItemDTO);
        if (feedItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new feedItem cannot already have an ID")).body(null);
        }
        FeedItemDTO result = feedItemService.save(feedItemDTO);
        return ResponseEntity.created(new URI("/api/feed-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /feed-items : Updates an existing feedItem.
     *
     * @param feedItemDTO the feedItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated feedItemDTO,
     * or with status 400 (Bad Request) if the feedItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the feedItemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/feed-items")
    @Timed
    public ResponseEntity<FeedItemDTO> updateFeedItem(@RequestBody FeedItemDTO feedItemDTO) throws URISyntaxException {
        log.debug("REST request to update FeedItem : {}", feedItemDTO);
        if (feedItemDTO.getId() == null) {
            return createFeedItem(feedItemDTO);
        }
        FeedItemDTO result = feedItemService.save(feedItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, feedItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /feed-items : get all the feedItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of feedItems in body
     */
    @GetMapping("/feed-items")
    @Timed
    public ResponseEntity<List<FeedItemDTO>> getAllFeedItems(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FeedItems");
        Page<FeedItemDTO> page = feedItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/feed-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /feed-items/:id : get the "id" feedItem.
     *
     * @param id the id of the feedItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feedItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/feed-items/{id}")
    @Timed
    public ResponseEntity<FeedItemDTO> getFeedItem(@PathVariable Long id) {
        log.debug("REST request to get FeedItem : {}", id);
        FeedItemDTO feedItemDTO = feedItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(feedItemDTO));
    }
    
    /**
     * DELETE  /feed-items/:id : delete the "id" feedItem.
     *
     * @param id the id of the feedItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/feed-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteFeedItem(@PathVariable Long id) {
        log.debug("REST request to delete FeedItem : {}", id);
        feedItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    @PostMapping("/feed/store-cloud")
    @Timed
    public ResponseEntity<FeedItemDTO> storeCloud(@RequestBody FeedItemDTO feedItemDTO) throws URISyntaxException {
        log.debug("REST request to save storeCloud : {}", feedItemDTO);
        FeedItemDTO result = feedItemService.storeCloud(feedItemDTO);
        if (null == result) {
        	return ResponseEntity.badRequest().body(null);
        }
        
        return ResponseEntity.created(new URI("/api/feed/store-clound"))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "store-cloud"))
            .body(result);
    }
    
    /**
     * GET  /feed-items : get all the feedItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of feedItems in body
     */
    @GetMapping("/feeds-by-user-post")
    @Timed
    public ResponseEntity<List<FeedItemDTO>> getAllFeedItemsByUser(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of getAllFeedItemsByUser");
        Page<FeedItemDTO> page = feedItemService.findAllByUserId(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/feeds-by-user");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /feed-items : get all the feedItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of feedItems in body
     */
    @GetMapping("/feeds-by-user-status")
    @Timed
    public ResponseEntity<List<FeedItemDTO>> getAllFeedItemsByUserLikeOrHate(@ApiParam Pageable pageable, @RequestParam("status") Integer status) {
        log.debug("REST request to getAllFeedItemsByUserLikeOrHate");
        Page<FeedItemDTO> page = feedItemService.findAllByUserIdAndStatus(pageable, status);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/feeds-by-user");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    @GetMapping("/feed/info/{id}")
    @Timed
    public ResponseEntity<FeedItemDTO> getFeedItemInfo(@PathVariable Long id) {
    	log.debug("REST request to get getFeedItemInfo : {}", id);
    	FeedItemDTO feedItemDTO = feedItemService.findOne(id);
    	
    	// check null
    	if (feedItemDTO == null) {
    		return new ResponseEntity<>(null, HttpStatus.OK);
    	}
    	
    	// summary data
    	TrendStatusDTO summaryData = new TrendStatusDTO();
        feedItemDTO.setStatusItem(summaryData);
        
        // Find all like or unlike
        List<StatusItemDTO> dataLike = statusItemService.findByItemIdAndStatus(id, BlastConstant.ITEM_STATUS_LIKE);
        List<StatusItemDTO> dataUnlike = statusItemService.findByItemIdAndStatus(id, BlastConstant.ITEM_STATUS_UNLIKE);
        
        // Set number
        if (dataLike != null && dataLike.size() > 0) {
        	List<UserDTO> lstUserLike = new ArrayList<>();
        	for (StatusItemDTO statusItemDTO : dataLike) {
        		UserDTO tmpUser = userService.getUserDTOWithDisplayName(statusItemDTO.getUserId());
        		lstUserLike.add(tmpUser);
			}
        	summaryData.setNumberLike(Long.valueOf(dataLike.size()));	
        	summaryData.setLstUserLike(lstUserLike);
        }
        if (dataUnlike != null && dataUnlike.size() > 0) {
        	List<UserDTO> lstUserUnlike = new ArrayList<>();
        	for (StatusItemDTO statusItemDTO : dataUnlike) {
        		UserDTO tmpUser = userService.getUserDTOWithDisplayName(statusItemDTO.getUserId());
        		lstUserUnlike.add(tmpUser);
			}
        	summaryData.setNumberHate(Long.valueOf(dataUnlike.size()));	
        	summaryData.setLstUserUnlike(lstUserUnlike);
        }
        
        return new ResponseEntity<>(feedItemDTO, null, HttpStatus.OK);
    }
    
    @GetMapping("/feeds-by-trend-friend")
    @Timed
    public ResponseEntity<List<FeedItemDTO>> getAllFeedItemsByTrendFriendLikeOrHate(@ApiParam Pageable pageable, @RequestParam("status") Integer status) {
        log.debug("REST request to getAllFeedItemsByTrendFriendLikeOrHate");
        Page<FeedItemDTO> page = feedItemService.findAllByTrendFriendAndStatus(pageable, status);
        if (page == null || page.getContent() == null) {
        	return new ResponseEntity<>(HttpStatus.OK);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/feeds-by-trend-friend");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    @GetMapping("/feeds-by-trend-all")
    @Timed
    public ResponseEntity<List<FeedItemDTO>> getAllFeedItemsByTrendAllLikeOrHate(@ApiParam Pageable pageable, @RequestParam("status") Integer status) {
        log.debug("REST request to getAllFeedItemsByTrendAllLikeOrHate");
        Page<FeedItemDTO> page = feedItemService.findAllByTrendAllAndStatus(pageable, status);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/feeds-by-trend-all");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    @GetMapping("/feeds-by-keyword")
    @Timed
    public ResponseEntity<FeedItemsByKeywordAndStatusResponse> getAllFeedItemsByKeywordAndStatus(@ApiParam Pageable pageable
    		, @RequestParam("status") Integer status
    		, @RequestParam("keyword") String keyword) {
        log.debug("REST request to getAllFeedItemsByKeywordAndStatus, {} {}", keyword, status);
        Page<FeedItemDTO> page = feedItemService.findAllByMainKeywordAndStatus(pageable, keyword, status);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/feeds-by-keyword");
        
        // Get keyword
        KeywordDTO keywordDTO = keywordService.findOneByName(keyword);
        FeedItemsByKeywordAndStatusResponse response = new FeedItemsByKeywordAndStatusResponse(keywordDTO, page.getContent());
        
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    
    @GetMapping("/top-keyword-by-friend")
    @Timed
    public ResponseEntity<List<KeywordDTO>> topKeywordByFriend(@ApiParam Pageable pageable
    		, @RequestParam("status") Integer status) {
        log.debug("REST request to topKeywordByFriend, {}", status);
        
        // Get list friend
        com.blast.domain.User user = userService.getCurrentUser();
		UserDTO userDTO = userService.getUserDTOWithDisplayName(user.getId());
		if (StringUtils.isEmpty(userDTO.getProviderId())) {
			return null;
		}
		List<Long> lstUserId = userService.getAllFollowingUserId(user.getId());
		// check
		if (lstUserId == null || lstUserId.size() == 0) {
			return new ResponseEntity<>(null, null, HttpStatus.OK);
		}
        
		Page<KeywordDTO> keywords = keywordService.findTopKeywordOfFriend(lstUserId, status, pageable);
		if (keywords.getContent() != null && keywords.getContent().size() > 0) {
			for (KeywordDTO keywordDTO : keywords) {
				FeedItemDTO item = feedItemService.findOneByMainKeywordFriend(lstUserId, keywordDTO.getName());
				keywordDTO.setRepresentationFeedItem(item);
			}
		}
		
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(keywords, "/api/top-keyword-by-friend");
        
        return new ResponseEntity<>(keywords.getContent(), headers, HttpStatus.OK);
    }
    
    @GetMapping("/top-keyword-by-all")
    @Timed
    public ResponseEntity<List<KeywordDTO>> topKeywordByAll(@ApiParam Pageable pageable
    		, @RequestParam("status") Integer status) {
		log.debug("REST request to topKeywordByAll, {}", status);
        
        // Get list friend
		Page<KeywordDTO> keywords = keywordService.findTopKeywordOfAll(status, pageable);
		if (keywords.getContent() != null && keywords.getContent().size() > 0) {
			for (KeywordDTO keywordDTO : keywords) {
				FeedItemDTO item = feedItemService.findOneByMainKeyword(keywordDTO.getName());
				keywordDTO.setRepresentationFeedItem(item);
			}
		}
		
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(keywords, "/api/top-keyword-by-all");
        
        return new ResponseEntity<>(keywords.getContent(), headers, HttpStatus.OK);
    }
}

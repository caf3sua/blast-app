package com.blast.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.blast.domain.SocialUserConnection;
import com.blast.repository.SocialUserConnectionRepository;
import com.blast.repository.UserRepository;
import com.blast.security.SecurityUtils;
import com.blast.service.SocialFollowService;
import com.blast.web.rest.util.HeaderUtil;
import com.blast.web.rest.util.PaginationUtil;
import com.blast.web.rest.vm.SocialFollowVM;
import com.blast.service.dto.SocialFollowDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SocialFollow.
 */
@RestController
@RequestMapping("/api")
public class SocialFollowResource {

    private final Logger log = LoggerFactory.getLogger(SocialFollowResource.class);

    private static final String ENTITY_NAME = "socialFollow";

    private final SocialFollowService socialFollowService;
    
    private final SocialUserConnectionRepository socialUserConnectionRepository;

    private final UserRepository userRepository;


    public SocialFollowResource(SocialFollowService socialFollowService
    		, SocialUserConnectionRepository socialUserConnectionRepository
    		, UserRepository userRepository) {
        this.socialFollowService = socialFollowService;
        this.socialUserConnectionRepository = socialUserConnectionRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /social-follows : Create a new socialFollow.
     *
     * @param socialFollowDTO the socialFollowDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new socialFollowDTO, or with status 400 (Bad Request) if the socialFollow has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/social-follows")
    @Timed
    public ResponseEntity<SocialFollowDTO> createSocialFollow(@Valid @RequestBody SocialFollowVM socialFollowVM) throws URISyntaxException {
        log.debug("REST request to save SocialFollow : {}", socialFollowVM);
        // Check exist
        SocialFollowDTO social = socialFollowService.getFollowing(socialFollowVM.getProviderId(), socialFollowVM.getProviderUserId(), socialFollowVM.getFollowUserId());
        if (social != null) {
        	return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);
        }
        
        // Convert DTO
        SocialFollowDTO data = new SocialFollowDTO(socialFollowVM.getProviderId(), socialFollowVM.getFollowUserId(), socialFollowVM.getProviderUserId());
        
        SocialFollowDTO result = socialFollowService.save(data);
        return ResponseEntity.created(new URI("/api/social-follows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /social-follows : Updates an existing socialFollow.
     *
     * @param socialFollowDTO the socialFollowDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated socialFollowDTO,
     * or with status 400 (Bad Request) if the socialFollowDTO is not valid,
     * or with status 500 (Internal Server Error) if the socialFollowDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/social-follows")
    @Timed
    public ResponseEntity<SocialFollowDTO> updateSocialFollow(@Valid @RequestBody SocialFollowDTO socialFollowDTO) throws URISyntaxException {
        log.debug("REST request to update SocialFollow : {}", socialFollowDTO);
        if (socialFollowDTO.getId() == null) {
        	SocialFollowVM socialFollowVM = new SocialFollowVM(socialFollowDTO.getProviderId(), socialFollowDTO.getProviderUserId()
        			, socialFollowDTO.getFollowUserId());
            return createSocialFollow(socialFollowVM);
        }
        SocialFollowDTO result = socialFollowService.save(socialFollowDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, socialFollowDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /social-follows : get all the socialFollows.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of socialFollows in body
     */
    @GetMapping("/social-follows/all")
    @Timed
    public ResponseEntity<List<SocialFollowDTO>> getAllSocialFollows() {
        log.debug("REST request to get a page of SocialFollows");
        List<SocialFollowDTO> data = socialFollowService.findAll();
        return new ResponseEntity<>(data, null, HttpStatus.OK);
    }
    
    /**
     * GET  /social-follows/followers : get all the socialFollows.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of socialFollows in body
     */
    @GetMapping("/social-follows/followers")
    @Timed
    public ResponseEntity<List<SocialFollowDTO>> getAllSocialUserFollowers(@RequestParam(value = "providerId") String providerId) {
        log.debug("REST request to get getAllSocialUserFollowers");
        
        // FInd current user and check provider
        String loginName = SecurityUtils.getCurrentUserLogin();
        List<SocialUserConnection> lstConnectionUser = socialUserConnectionRepository.findAllByUserIdAndProviderIdOrderByRankAsc(loginName, providerId);
        
        if (null == lstConnectionUser || lstConnectionUser.size() == 0) {
        	return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Account is invalid"))
                    .body(null);
        }
        
        SocialUserConnection sUser = lstConnectionUser.get(0);
        
        List<SocialFollowDTO> data = socialFollowService.getFollowers(providerId, sUser.getProviderUserId());
        for (SocialFollowDTO socialFollowDTO : data) {
        	List<SocialUserConnection> lstConnection = socialUserConnectionRepository.findAllByProviderIdAndProviderUserId(socialFollowDTO.getProviderId()
        			, socialFollowDTO.getProviderUserId());
        	if (lstConnection != null) {
        		SocialUserConnection tmp = lstConnection.get(0);
        		socialFollowDTO.setDisplayName(tmp.getDisplayName());
        		socialFollowDTO.setImageUrl(tmp.getImageURL());
        	}
		}
        return new ResponseEntity<>(data, null, HttpStatus.OK);
    }
    
    /**
     * GET  /social-follows/following : get all the socialFollowings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of socialFollows in body
     */
    @GetMapping("/social-follows/following")
    @Timed
    public ResponseEntity<List<SocialFollowDTO>> getAllSocialUserFollowing(@RequestParam(value = "providerId") String providerId) {
        log.debug("REST request to get getAllSocialUserFollowering");
        
        // FInd current user and check provider
        String loginName = SecurityUtils.getCurrentUserLogin();
        List<SocialUserConnection> lstConnectionUser = socialUserConnectionRepository.findAllByUserIdAndProviderIdOrderByRankAsc(loginName, providerId);
        
        if (null == lstConnectionUser || lstConnectionUser.size() == 0) {
        	return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Account is invalid"))
                    .body(null);
        }
        
        List<SocialFollowDTO> dataResult = new ArrayList<>();
        
        for (SocialUserConnection sUser : lstConnectionUser) {
            List<SocialFollowDTO> data = socialFollowService.getFollowing(providerId, sUser.getProviderUserId());
            for (SocialFollowDTO socialFollowDTO : data) {
            	List<SocialUserConnection> lstConnection = socialUserConnectionRepository.findAllByProviderIdAndProviderUserId(socialFollowDTO.getProviderId()
            			, socialFollowDTO.getFollowUserId());
            	if (lstConnection != null && lstConnection.size() != 0) {
            		SocialUserConnection tmp = lstConnection.get(0);
            		socialFollowDTO.setDisplayName(tmp.getDisplayName());
            		socialFollowDTO.setImageUrl(tmp.getImageURL());
            	}
    		}
            
            dataResult.addAll(data);
		}
        
        return new ResponseEntity<>(dataResult, null, HttpStatus.OK);
    }
    
    /**
     * GET  /social-follows/following : get all the socialFollowings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of socialFollows in body
     */
    @GetMapping("/social-follows/check-following")
    @Timed
    public ResponseEntity<SocialFollowDTO> checkSocialUserFollowing(@RequestParam(value = "providerId") String providerId
    		, @RequestParam(value = "providerUserId") String providerUserId) {
        log.debug("REST request to get checkSocialUserFollowing");
        
        // FInd current user and check provider
        String loginName = SecurityUtils.getCurrentUserLogin();
        List<SocialUserConnection> lstConnectionUser = socialUserConnectionRepository.findAllByUserIdAndProviderIdOrderByRankAsc(loginName, providerId);
        
        if (null == lstConnectionUser || lstConnectionUser.size() == 0) {
        	return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Account is invalid"))
                    .body(null);
        }
        
        for (SocialUserConnection sUser : lstConnectionUser) {
        	SocialFollowDTO data = socialFollowService.getFollowing(providerId, sUser.getProviderUserId(), providerUserId);
            
        	if (data != null) {
        		List<SocialUserConnection> lstConnection = socialUserConnectionRepository.findAllByProviderIdAndProviderUserId(data.getProviderId()
            			, data.getFollowUserId());
            	if (lstConnection != null) {
            		SocialUserConnection tmp = lstConnection.get(0);
            		data.setDisplayName(tmp.getDisplayName());
            		data.setImageUrl(tmp.getImageURL());
            		return new ResponseEntity<>(data, HttpStatus.OK);
            	}
        	}
		}
        
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    /**
     * GET  /social-follows : get all the socialFollows with paging.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of socialFollows in body
     */
    @GetMapping("/social-follows")
    @Timed
    public ResponseEntity<List<SocialFollowDTO>> getAllSocialFollowsWithPaging(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SocialFollows");
        Page<SocialFollowDTO> page = socialFollowService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/social-follows");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /social-follows/:id : get the "id" socialFollow.
     *
     * @param id the id of the socialFollowDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the socialFollowDTO, or with status 404 (Not Found)
     */
    @GetMapping("/social-follows/{id}")
    @Timed
    public ResponseEntity<SocialFollowDTO> getSocialFollow(@PathVariable Long id) {
        log.debug("REST request to get SocialFollow : {}", id);
        SocialFollowDTO socialFollowDTO = socialFollowService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(socialFollowDTO));
    }

    /**
     * DELETE  /social-follows/:id : delete the "id" socialFollow.
     *
     * @param id the id of the socialFollowDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/social-follows/{id}")
    @Timed
    public ResponseEntity<Void> deleteSocialFollow(@PathVariable Long id) {
        log.debug("REST request to delete SocialFollow : {}", id);
        socialFollowService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

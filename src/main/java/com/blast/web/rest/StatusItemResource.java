package com.blast.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.blast.domain.User;
import com.blast.repository.UserRepository;
import com.blast.security.SecurityUtils;
import com.blast.service.FeedItemService;
import com.blast.service.KeywordService;
import com.blast.service.StatusItemService;
import com.blast.web.rest.util.HeaderUtil;
import com.blast.web.rest.util.PaginationUtil;
import com.blast.service.dto.FeedItemDTO;
import com.blast.service.dto.KeywordDTO;
import com.blast.service.dto.StatusItemDTO;
import com.blast.service.dto.TrendStatusDTO;
import com.blast.service.util.BlastConstant;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StatusItem.
 */
@RestController
@RequestMapping("/api")
public class StatusItemResource {

    private final Logger log = LoggerFactory.getLogger(StatusItemResource.class);

    private static final String ENTITY_NAME = "statusItem";

    private final StatusItemService statusItemService;
    
    private final KeywordService keywordService;
    
    private final FeedItemService feedItemService;
    
    private final UserRepository userRepository;

    public StatusItemResource(StatusItemService statusItemService, UserRepository userRepository
    		, KeywordService keywordService
    		, FeedItemService feedItemService) {
        this.statusItemService = statusItemService;
        this.userRepository = userRepository;
        this.keywordService = keywordService;
        this.feedItemService = feedItemService;
    }

    /**
     * POST  /status-items : Create a new statusItem.
     *
     * @param statusItemDTO the statusItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new statusItemDTO, or with status 400 (Bad Request) if the statusItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/status-items")
    @Timed
    public ResponseEntity<StatusItemDTO> createStatusItem(@RequestBody StatusItemDTO statusItemDTO) throws URISyntaxException {
        log.debug("REST request to save StatusItem : {}", statusItemDTO);
        if (statusItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new statusItem cannot already have an ID")).body(null);
        }
        StatusItemDTO result = statusItemService.save(statusItemDTO);
        return ResponseEntity.created(new URI("/api/status-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status-items : Updates an existing statusItem.
     *
     * @param statusItemDTO the statusItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated statusItemDTO,
     * or with status 400 (Bad Request) if the statusItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the statusItemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/status-items")
    @Timed
    public ResponseEntity<StatusItemDTO> updateStatusItem(@RequestBody StatusItemDTO statusItemDTO) throws URISyntaxException {
        log.debug("REST request to update StatusItem : {}", statusItemDTO);
        if (statusItemDTO.getId() == null) {
            return createStatusItem(statusItemDTO);
        }
        StatusItemDTO result = statusItemService.save(statusItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, statusItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status-items : get all the statusItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of statusItems in body
     */
    @GetMapping("/status-items")
    @Timed
    public ResponseEntity<List<StatusItemDTO>> getAllStatusItems(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of StatusItems");
        Page<StatusItemDTO> page = statusItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/status-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /status-items/:id : get the "id" statusItem.
     *
     * @param id the id of the statusItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the statusItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/status-items/{id}")
    @Timed
    public ResponseEntity<StatusItemDTO> getStatusItem(@PathVariable Long id) {
        log.debug("REST request to get StatusItem : {}", id);
        StatusItemDTO statusItemDTO = statusItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(statusItemDTO));
    }

    /**
     * DELETE  /status-items/:id : delete the "id" statusItem.
     *
     * @param id the id of the statusItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/status-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteStatusItem(@PathVariable Long id) {
        log.debug("REST request to delete StatusItem : {}", id);
        statusItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * GET  /status/like/{itemId} : like item
     *
     * @param Long itemId
     * @return the ResponseEntity with status 200 (OK) and the list of statusItems in body
     * @throws URISyntaxException 
     */
    @ApiOperation(value = "BLAST - Like item API",
    	    response = StatusItemDTO.class)
    @GetMapping("/status/like/{itemId}")
    @Timed
    public ResponseEntity<StatusItemDTO> likeItem(@PathVariable Long itemId) throws URISyntaxException {
        log.debug("REST request to likeItem");
        final String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> existingUser = userRepository.findOneWithAuthoritiesByLogin(userLogin);
        
        StatusItemDTO statusItemDTO = new StatusItemDTO();
        statusItemDTO.setItemId(itemId);
        statusItemDTO.setUserId(existingUser.get().getId());
        statusItemDTO.setStatus(1);// like
        statusItemDTO.setCreatedDate(LocalDate.now());
        StatusItemDTO result = statusItemService.save(statusItemDTO);
        
        // Get item -> to find main keyword
        FeedItemDTO item = feedItemService.findOne(itemId);
        if (item != null) {
        	String keyword = item.getMainKeyword();
            // Update keyword count : like
        	keywordService.updateStatus(keyword, BlastConstant.ITEM_STATUS_LIKE);
        }
        
        return ResponseEntity.created(new URI("/api/status/like" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
	@ApiOperation(value = "BLAST - Check status item : like/hate chưa",
    	    response = StatusItemDTO.class)
    @GetMapping("/status/check/{itemId}")
    @Timed
    public ResponseEntity<StatusItemDTO> checkStatusItem(@PathVariable Long itemId) throws URISyntaxException {
        log.debug("REST request to checkStatusItem");
        final String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> existingUser = userRepository.findOneWithAuthoritiesByLogin(userLogin);
        
        // instance object
        StatusItemDTO result = new StatusItemDTO();
        result.setItemId(itemId);
        result.setUserId(existingUser.get().getId());
        result.setLike(false);
        result.setHate(false);
        // Get status
        List<StatusItemDTO> status = statusItemService.findByItemIdAndUserId(itemId, existingUser.get().getId());
        if (status != null) {
        	for (StatusItemDTO item : status) {
    			if (item.getStatus() == BlastConstant.ITEM_STATUS_LIKE) {
    				result.setLike(true);
    			} else if (item.getStatus() == BlastConstant.ITEM_STATUS_UNLIKE) {
    				result.setHate(true);
    			}
    		}
        }

        return new ResponseEntity<>(result, null, HttpStatus.OK);
    }
    
    /**
     * GET  /status/unlike/{itemId} : like item
     *
     * @param Long itemId
     * @return the ResponseEntity with status 200 (OK) and the list of statusItems in body
     * @throws URISyntaxException 
     */
    @ApiOperation(value = "BLAST - Hate item API",
    	    response = StatusItemDTO.class)
    @GetMapping("/status/unlike/{itemId}")
    @Timed
    public ResponseEntity<StatusItemDTO> unlikeItem(@PathVariable Long itemId) throws URISyntaxException {
        log.debug("REST request to unlikeItem");
        final String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> existingUser = userRepository.findOneWithAuthoritiesByLogin(userLogin);
        
        StatusItemDTO statusItemDTO = new StatusItemDTO();
        statusItemDTO.setItemId(itemId);
        statusItemDTO.setUserId(existingUser.get().getId());
        statusItemDTO.setStatus(0);// unlike
        statusItemDTO.setCreatedDate(LocalDate.now());
        StatusItemDTO result = statusItemService.save(statusItemDTO);
        
        // Get item -> to find main keyword
        FeedItemDTO item = feedItemService.findOne(itemId);
        if (item != null) {
        	String keyword = item.getMainKeyword();
            // Update keyword count : hate
        	keywordService.updateStatus(keyword, BlastConstant.ITEM_STATUS_UNLIKE);
        }
        return ResponseEntity.created(new URI("/api/status/like" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    
    /**
     * GET  /status/get-like : get all the statusItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of statusItems in body
     */
    @ApiOperation(value = "BLAST - Lấy danh sách Item mà người dùng đã like",
    		notes = "API này có chức năng phân trang và sort",
    	    response = StatusItemDTO.class,
    	    responseContainer = "List")
    @GetMapping("/status/get-like")
    @Timed
    public ResponseEntity<List<StatusItemDTO>> getLikeItems(@ApiParam Pageable pageable) {
        log.debug("REST request to getLikeItems");
        final String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> existingUser = userRepository.findOneWithAuthoritiesByLogin(userLogin);
        
        Page<StatusItemDTO> page = statusItemService.findByUserIdAndStatus(existingUser.get().getId()
        		, 1, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/get-like");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /status/get-like : get all the statusItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of statusItems in body
     */
    @ApiOperation(value = "BLAST - Lấy danh sách Item mà người dùng đã unlike",
    		notes = "API này có chức năng phân trang và sort",
    	    response = StatusItemDTO.class,
    	    responseContainer = "List")
    @GetMapping("/status/get-unlike")
    @Timed
    public ResponseEntity<List<StatusItemDTO>> getUnlikeItems(@ApiParam Pageable pageable) {
        log.debug("REST request to getUnlikeItems");
        final String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> existingUser = userRepository.findOneWithAuthoritiesByLogin(userLogin);
        
        Page<StatusItemDTO> page = statusItemService.findByUserIdAndStatus(existingUser.get().getId()
        		, 0, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/get-unlike");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /status/trend/owner : get all the statusItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of statusItems in body
     */
    @ApiOperation(value = "BLAST - Lấy danh sách thống kê số lượng like/unlike mà người dùng đã thực hiện trong khoảng thời gian fromDate và endDate",
    		notes = "field fromDate và endDate có format yyyyMMdd",
    	    response = TrendStatusDTO.class,
    	    responseContainer = "List")
    @GetMapping("/status/trend/owner")
    @Timed
    public ResponseEntity<List<TrendStatusDTO>> trendOwner(@RequestParam("fromDate") String yyyyMMddFromDate, @RequestParam("endDate") String yyyyMMddEndDate) {
        log.debug("REST request to getUnlikeItems");
        final String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> existingUser = userRepository.findOneWithAuthoritiesByLogin(userLogin);
        
        List<TrendStatusDTO> data = statusItemService.trendOwner(existingUser.get().getId()
        		, yyyyMMddFromDate, yyyyMMddEndDate);
        return new ResponseEntity<>(data, null, HttpStatus.OK);
    }
    
    @ApiOperation(value = "BLAST - Lấy danh sách thống kê số lượng like/unlike mà người bạn bè/người bất kỳ đã thực hiện trên các item của user trong khoảng thời gian fromDate và endDate",
    		notes = "field fromDate và endDate có format yyyyMMdd",
    	    response = TrendStatusDTO.class,
    	    responseContainer = "List")
    @GetMapping("/status/trend/friend")
    @Timed
    public ResponseEntity<List<TrendStatusDTO>> trendFriend(@RequestParam("fromDate") String yyyyMMddFromDate, @RequestParam("endDate") String yyyyMMddEndDate) {
        log.debug("REST request to trendFriend");
        final String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> existingUser = userRepository.findOneWithAuthoritiesByLogin(userLogin);
        
        List<TrendStatusDTO> data = statusItemService.trendFriend(existingUser.get().getId()
        		, yyyyMMddFromDate, yyyyMMddEndDate);
        return new ResponseEntity<>(data, null, HttpStatus.OK);
    }
}

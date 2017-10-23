package com.blast.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.blast.service.KeywordService;
import com.blast.service.UserService;
import com.blast.web.rest.util.HeaderUtil;
import com.blast.web.rest.util.PaginationUtil;
import com.blast.service.dto.KeywordDTO;
import com.blast.service.dto.UserDTO;
import com.blast.service.dto.response.KeywordInfoResponse;
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
 * REST controller for managing Keyword.
 */
@RestController
@RequestMapping("/api")
public class KeywordResource {

    private final Logger log = LoggerFactory.getLogger(KeywordResource.class);

    private static final String ENTITY_NAME = "keyword";

    private final KeywordService keywordService;
    
    private final UserService userService;
    

    public KeywordResource(KeywordService keywordService
    		, UserService userService) {
        this.keywordService = keywordService;
        this.userService = userService;
    }

    /**
     * POST  /keywords : Create a new keyword.
     *
     * @param keywordDTO the keywordDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new keywordDTO, or with status 400 (Bad Request) if the keyword has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/keywords")
    @Timed
    public ResponseEntity<KeywordDTO> createKeyword(@RequestBody KeywordDTO keywordDTO) throws URISyntaxException {
        log.debug("REST request to save Keyword : {}", keywordDTO);
        if (keywordDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new keyword cannot already have an ID")).body(null);
        }
        KeywordDTO result = keywordService.save(keywordDTO);
        return ResponseEntity.created(new URI("/api/keywords/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /keywords : Updates an existing keyword.
     *
     * @param keywordDTO the keywordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated keywordDTO,
     * or with status 400 (Bad Request) if the keywordDTO is not valid,
     * or with status 500 (Internal Server Error) if the keywordDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/keywords")
    @Timed
    public ResponseEntity<KeywordDTO> updateKeyword(@RequestBody KeywordDTO keywordDTO) throws URISyntaxException {
        log.debug("REST request to update Keyword : {}", keywordDTO);
        if (keywordDTO.getId() == null) {
            return createKeyword(keywordDTO);
        }
        KeywordDTO result = keywordService.save(keywordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, keywordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /keywords : get all the keywords.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of keywords in body
     */
    @GetMapping("/keywords")
    @Timed
    public ResponseEntity<List<KeywordDTO>> getAllKeywords(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Keywords");
        Page<KeywordDTO> page = keywordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/keywords");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /keywords/:id : get the "id" keyword.
     *
     * @param id the id of the keywordDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the keywordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/keywords/{id}")
    @Timed
    public ResponseEntity<KeywordDTO> getKeyword(@PathVariable Long id) {
        log.debug("REST request to get Keyword : {}", id);
        KeywordDTO keywordDTO = keywordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(keywordDTO));
    }
    
    @GetMapping("/keyword-info")
    @Timed
    public ResponseEntity<KeywordInfoResponse> getKeywordInfo(@RequestParam("name") String name) {
    	KeywordInfoResponse res = new KeywordInfoResponse();
        log.debug("REST request to get getKeywordInfo : {}", name);
        
        // Lower case
        name = StringUtils.lowerCase(name);
        
        KeywordDTO keywordDTO = keywordService.findOneByName(name);
        
        // Find user
        List<Long> lstUserIdLike = keywordService.findLatestUserIdByKeyword(BlastConstant.ITEM_STATUS_LIKE, name);
        List<Long> lstUserIdHate = keywordService.findLatestUserIdByKeyword(BlastConstant.ITEM_STATUS_UNLIKE, name);
        
        
        List<UserDTO> lstUserDTOLike = new ArrayList<>();
		List<UserDTO> lstUserDTOHate = new ArrayList<>();
        
        for (Long index : lstUserIdLike) {
        	UserDTO userDTO = userService.getUserDTOWithDisplayName(index);
        	lstUserDTOLike.add(userDTO);
		}
        
        
        for (Long index : lstUserIdHate) {
        	UserDTO userDTO = userService.getUserDTOWithDisplayName(index);
        	lstUserDTOHate.add(userDTO);
		}
        
        // set data return
        res.setKeyword(keywordDTO);
        res.setLstUserLike(lstUserDTOLike);
        res.setLstUserHate(lstUserDTOHate);
        
        return new ResponseEntity<>(res, null, HttpStatus.OK);
    }

    @GetMapping("/keyword-by-name")
    @Timed
    public ResponseEntity<KeywordDTO> getKeywordByName(@RequestParam("name") String name) {
        log.debug("REST request to get getKeywordInfo : {}", name);
        KeywordDTO keywordDTO = keywordService.findOneByName(StringUtils.lowerCase(name));
        
        return new ResponseEntity<>(keywordDTO, null, HttpStatus.OK);
    }
    
    /**
     * DELETE  /keywords/:id : delete the "id" keyword.
     *
     * @param id the id of the keywordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/keywords/{id}")
    @Timed
    public ResponseEntity<Void> deleteKeyword(@PathVariable Long id) {
        log.debug("REST request to delete Keyword : {}", id);
        keywordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

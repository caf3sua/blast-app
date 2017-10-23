package com.blast.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.blast.service.KeywordUserService;
import com.blast.web.rest.util.HeaderUtil;
import com.blast.web.rest.util.PaginationUtil;
import com.blast.service.dto.KeywordUserDTO;
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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing KeywordUser.
 */
@RestController
@RequestMapping("/api")
public class KeywordUserResource {

    private final Logger log = LoggerFactory.getLogger(KeywordUserResource.class);

    private static final String ENTITY_NAME = "keywordUser";

    private final KeywordUserService keywordUserService;

    public KeywordUserResource(KeywordUserService keywordUserService) {
        this.keywordUserService = keywordUserService;
    }

    /**
     * POST  /keyword-users : Create a new keywordUser.
     *
     * @param keywordUserDTO the keywordUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new keywordUserDTO, or with status 400 (Bad Request) if the keywordUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/keyword-users")
    @Timed
    public ResponseEntity<KeywordUserDTO> createKeywordUser(@RequestBody KeywordUserDTO keywordUserDTO) throws URISyntaxException {
        log.debug("REST request to save KeywordUser : {}", keywordUserDTO);
        if (keywordUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new keywordUser cannot already have an ID")).body(null);
        }
        KeywordUserDTO result = keywordUserService.save(keywordUserDTO);
        return ResponseEntity.created(new URI("/api/keyword-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /keyword-users : Updates an existing keywordUser.
     *
     * @param keywordUserDTO the keywordUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated keywordUserDTO,
     * or with status 400 (Bad Request) if the keywordUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the keywordUserDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/keyword-users")
    @Timed
    public ResponseEntity<KeywordUserDTO> updateKeywordUser(@RequestBody KeywordUserDTO keywordUserDTO) throws URISyntaxException {
        log.debug("REST request to update KeywordUser : {}", keywordUserDTO);
        if (keywordUserDTO.getId() == null) {
            return createKeywordUser(keywordUserDTO);
        }
        KeywordUserDTO result = keywordUserService.save(keywordUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, keywordUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /keyword-users : get all the keywordUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of keywordUsers in body
     */
    @GetMapping("/keyword-users")
    @Timed
    public ResponseEntity<List<KeywordUserDTO>> getAllKeywordUsers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of KeywordUsers");
        Page<KeywordUserDTO> page = keywordUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/keyword-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /keyword-users/:id : get the "id" keywordUser.
     *
     * @param id the id of the keywordUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the keywordUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/keyword-users/{id}")
    @Timed
    public ResponseEntity<KeywordUserDTO> getKeywordUser(@PathVariable Long id) {
        log.debug("REST request to get KeywordUser : {}", id);
        KeywordUserDTO keywordUserDTO = keywordUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(keywordUserDTO));
    }

    /**
     * DELETE  /keyword-users/:id : delete the "id" keywordUser.
     *
     * @param id the id of the keywordUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/keyword-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteKeywordUser(@PathVariable Long id) {
        log.debug("REST request to delete KeywordUser : {}", id);
        keywordUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

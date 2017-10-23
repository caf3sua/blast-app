package com.blast.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.blast.service.TrendService;
import com.blast.web.rest.util.HeaderUtil;
import com.blast.web.rest.util.PaginationUtil;
import com.blast.service.dto.TrendDTO;
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
 * REST controller for managing Trend.
 */
@RestController
@RequestMapping("/api")
public class TrendResource {

    private final Logger log = LoggerFactory.getLogger(TrendResource.class);

    private static final String ENTITY_NAME = "trend";

    private final TrendService trendService;

    public TrendResource(TrendService trendService) {
        this.trendService = trendService;
    }

    /**
     * POST  /trends : Create a new trend.
     *
     * @param trendDTO the trendDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trendDTO, or with status 400 (Bad Request) if the trend has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trends")
    @Timed
    public ResponseEntity<TrendDTO> createTrend(@RequestBody TrendDTO trendDTO) throws URISyntaxException {
        log.debug("REST request to save Trend : {}", trendDTO);
        if (trendDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new trend cannot already have an ID")).body(null);
        }
        TrendDTO result = trendService.save(trendDTO);
        return ResponseEntity.created(new URI("/api/trends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trends : Updates an existing trend.
     *
     * @param trendDTO the trendDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trendDTO,
     * or with status 400 (Bad Request) if the trendDTO is not valid,
     * or with status 500 (Internal Server Error) if the trendDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trends")
    @Timed
    public ResponseEntity<TrendDTO> updateTrend(@RequestBody TrendDTO trendDTO) throws URISyntaxException {
        log.debug("REST request to update Trend : {}", trendDTO);
        if (trendDTO.getId() == null) {
            return createTrend(trendDTO);
        }
        TrendDTO result = trendService.save(trendDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trendDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trends : get all the trends.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trends in body
     */
    @GetMapping("/trends")
    @Timed
    public ResponseEntity<List<TrendDTO>> getAllTrends(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Trends");
        Page<TrendDTO> page = trendService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trends");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trends/:id : get the "id" trend.
     *
     * @param id the id of the trendDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trendDTO, or with status 404 (Not Found)
     */
    @GetMapping("/trends/{id}")
    @Timed
    public ResponseEntity<TrendDTO> getTrend(@PathVariable Long id) {
        log.debug("REST request to get Trend : {}", id);
        TrendDTO trendDTO = trendService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(trendDTO));
    }

    /**
     * DELETE  /trends/:id : delete the "id" trend.
     *
     * @param id the id of the trendDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trends/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrend(@PathVariable Long id) {
        log.debug("REST request to delete Trend : {}", id);
        trendService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

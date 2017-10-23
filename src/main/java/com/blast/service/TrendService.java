package com.blast.service;

import com.blast.service.dto.TrendDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Trend.
 */
public interface TrendService {

    /**
     * Save a trend.
     *
     * @param trendDTO the entity to save
     * @return the persisted entity
     */
    TrendDTO save(TrendDTO trendDTO);

    /**
     *  Get all the trends.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TrendDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" trend.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TrendDTO findOne(Long id);

    /**
     *  Delete the "id" trend.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

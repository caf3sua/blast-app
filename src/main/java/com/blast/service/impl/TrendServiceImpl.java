package com.blast.service.impl;

import com.blast.service.TrendService;
import com.blast.domain.Trend;
import com.blast.repository.TrendRepository;
import com.blast.service.dto.TrendDTO;
import com.blast.service.mapper.TrendMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Trend.
 */
@Service
@Transactional
public class TrendServiceImpl implements TrendService{

    private final Logger log = LoggerFactory.getLogger(TrendServiceImpl.class);

    private final TrendRepository trendRepository;

    private final TrendMapper trendMapper;

    public TrendServiceImpl(TrendRepository trendRepository, TrendMapper trendMapper) {
        this.trendRepository = trendRepository;
        this.trendMapper = trendMapper;
    }

    /**
     * Save a trend.
     *
     * @param trendDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TrendDTO save(TrendDTO trendDTO) {
        log.debug("Request to save Trend : {}", trendDTO);
        Trend trend = trendMapper.toEntity(trendDTO);
        trend = trendRepository.save(trend);
        return trendMapper.toDto(trend);
    }

    /**
     *  Get all the trends.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TrendDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trends");
        return trendRepository.findAll(pageable)
            .map(trendMapper::toDto);
    }

    /**
     *  Get one trend by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TrendDTO findOne(Long id) {
        log.debug("Request to get Trend : {}", id);
        Trend trend = trendRepository.findOne(id);
        return trendMapper.toDto(trend);
    }

    /**
     *  Delete the  trend by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trend : {}", id);
        trendRepository.delete(id);
    }
}

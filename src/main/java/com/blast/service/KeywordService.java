package com.blast.service;

import com.blast.service.dto.FeedItemDTO;
import com.blast.service.dto.KeywordDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Keyword.
 */
public interface KeywordService {
	
	KeywordDTO updateStatus(String keyword, Integer status);

    /**
     * Save a keyword.
     *
     * @param keywordDTO the entity to save
     * @return the persisted entity
     */
    KeywordDTO save(KeywordDTO keywordDTO);

    /**
     *  Get all the keywords.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<KeywordDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" keyword.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    KeywordDTO findOne(Long id);
    
    KeywordDTO findOneByName(String name);

    Page<KeywordDTO> findTopKeywordOfFriend(List<Long> lstUserId, Integer status, Pageable pageable);
    
    Page<KeywordDTO> findTopKeywordOfAll(Integer status, Pageable pageable);
    
    List<Long> findLatestUserIdByKeyword(Integer status, String keyword);
    /**
     *  Delete the "id" keyword.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

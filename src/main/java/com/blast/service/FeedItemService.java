package com.blast.service;

import com.blast.service.dto.FeedItemDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FeedItem.
 */
public interface FeedItemService {
	
	FeedItemDTO storeCloud(FeedItemDTO feedItemDTO);
    /**
     * Save a feedItem.
     *
     * @param feedItemDTO the entity to save
     * @return the persisted entity
     */
    FeedItemDTO save(FeedItemDTO feedItemDTO);

    /**
     *  Get all the feedItems.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FeedItemDTO> findAll(Pageable pageable);
    
    Page<FeedItemDTO> findAllByUserId(Pageable pageable);
    
    Page<FeedItemDTO> findAllByUserIdAndStatus(Pageable pageable, Integer status);
    
    Page<FeedItemDTO> findAllByTrendFriendAndStatus(Pageable pageable, Integer status);
    
    Page<FeedItemDTO> findAllByTrendAllAndStatus(Pageable pageable, Integer status);
    
    Page<FeedItemDTO> findAllByMainKeywordAndStatus(Pageable pageable, String keyword, Integer status);
    
    FeedItemDTO findOneByMainKeyword(String keyword);
    
    FeedItemDTO findOneByMainKeywordFriend(List<Long> lstUserId, String keyword);

    /**
     *  Get the "id" feedItem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FeedItemDTO findOne(Long id);

    /**
     *  Delete the "id" feedItem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

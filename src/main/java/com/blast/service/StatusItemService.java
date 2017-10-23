package com.blast.service;

import com.blast.service.dto.StatusItemDTO;
import com.blast.service.dto.TrendStatusDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing StatusItem.
 */
public interface StatusItemService {

	Page<StatusItemDTO> findByUserIdAndStatus(Long userId, Integer status, Pageable pageable);
	
	List<TrendStatusDTO> trendOwner(Long userId, String fromDate, String toDate);
	
	List<TrendStatusDTO> trendFriend(Long userId, String fromDate, String toDate);
	
	List<StatusItemDTO> findByItemIdAndStatus(Long itemId, Integer status);
	
	List<StatusItemDTO> findByItemIdAndUserId(Long itemId, Long userId);
	
    /**
     * Save a statusItem.
     *
     * @param statusItemDTO the entity to save
     * @return the persisted entity
     */
    StatusItemDTO save(StatusItemDTO statusItemDTO);

    /**
     *  Get all the statusItems.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<StatusItemDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" statusItem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StatusItemDTO findOne(Long id);

    /**
     *  Delete the "id" statusItem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

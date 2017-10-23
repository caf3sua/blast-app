package com.blast.repository;

import com.blast.domain.FeedItem;
import com.blast.service.dto.FeedItemDTO;
import com.blast.service.dto.TrendStatusDTO;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FeedItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusItemRepositoryExtend {
	List<TrendStatusDTO> countByUserIdAndCreatedDateRanger(Long userId, String fromDate, String toDate);	
	
	List<TrendStatusDTO> countByItemIdAndCreatedDateRanger(Long userId, String fromDate, String toDate);
}

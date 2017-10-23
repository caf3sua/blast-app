package com.blast.repository;

import com.blast.domain.FeedItem;
import com.blast.service.dto.FeedItemDTO;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FeedItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedItemRepository extends JpaRepository<FeedItem,Long>, FeedItemRepositoryExtend {
	
	Page<FeedItem> findByUserId(Long userId, Pageable pageable);
	
	Page<FeedItem> findByMainKeywordAndStatus(String mainKeyword, Integer status, Pageable pageable);
	
	FeedItem findFirstByMainKeywordOrderByCreatedDateDesc(String mainKeyword);
}

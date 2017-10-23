package com.blast.repository;

import com.blast.domain.FeedItem;
import com.blast.service.dto.FeedItemDTO;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FeedItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedItemRepositoryExtend {
	
	Page<FeedItem> findByUserIdAndStatus(Long userId, Integer status, Pageable pageable);
	
	Page<FeedItem> findAllByTrendFriendAndStatus(List<Long> friendUserId, Integer status, Pageable pageable);
	
	Page<FeedItem> findAllByTrendAllAndStatus(Integer status, Pageable pageable);
	
	FeedItem findFirstByMainKeywordFriend(List<Long> lstUserId, String mainKeyword);
}

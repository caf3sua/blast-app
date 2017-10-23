package com.blast.repository;

import com.blast.domain.FeedItem;
import com.blast.domain.Keyword;
import com.blast.service.dto.FeedItemDTO;
import com.blast.service.dto.KeywordDTO;

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
public interface KeywordRepositoryExtend {
	Page<Keyword> findTopKeywordOfFriend(List<Long> lstUserId, Integer status, Pageable pageable);
    
    Page<Keyword> findTopKeywordOfAll(Integer status, Pageable pageable);
    
    List<Long> findLatestUserIdByKeyword(Integer number, Integer status, String name);
}

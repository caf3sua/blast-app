package com.blast.repository;

import com.blast.domain.StatusItem;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StatusItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusItemRepository extends JpaRepository<StatusItem,Long>, StatusItemRepositoryExtend {

	Page<StatusItem> findByUserIdAndStatus(Long userId, Integer status, Pageable pageable);
	
	List<StatusItem> findByItemIdAndStatus(Long itemId, Integer status);
	
	Long countByUserIdAndCreatedDate(Long userId, LocalDate createdDate);
	
	List<StatusItem> findByItemIdAndUserId(Long itemId, Long userId);
	
	// custom query example and return a stream
    //@Query("select c from Customer c where c.email = :email")
    //Long[] countByUserIdAndCreateDateBetween(Long userId, LocalDate fromDate, LocalDate toDate);
}

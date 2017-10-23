package com.blast.repository;

import com.blast.domain.Keyword;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Keyword entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeywordRepository extends JpaRepository<Keyword,Long>, KeywordRepositoryExtend {
	
	Keyword findOneByName(String name);
}

package com.blast.repository;

import com.blast.domain.Trend;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Trend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrendRepository extends JpaRepository<Trend,Long> {

}

package com.blast.repository;

import com.blast.domain.KeywordUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the KeywordUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeywordUserRepository extends JpaRepository<KeywordUser,Long> {

}

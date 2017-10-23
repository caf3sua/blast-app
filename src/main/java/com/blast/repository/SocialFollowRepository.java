package com.blast.repository;

import com.blast.domain.SocialFollow;
import com.blast.domain.SocialUserConnection;
import com.blast.service.dto.SocialFollowDTO;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SocialFollow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocialFollowRepository extends JpaRepository<SocialFollow,Long> {
	
    // Get following
	List<SocialFollow> findAllByProviderIdAndProviderUserId(String providerId, String providerUserId);
	
	// Get followers
	List<SocialFollow> findAllByProviderIdAndFollowUserId(String providerId, String followUserId);
	
	SocialFollow findOneByProviderIdAndProviderUserIdAndFollowUserId(String providerId, String providerUserId, String followUserId);
}

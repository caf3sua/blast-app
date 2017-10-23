package com.blast.service;

import com.blast.service.dto.SocialFollowDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SocialFollow.
 */
public interface SocialFollowService {

    /**
     * Save a socialFollow.
     *
     * @param socialFollowDTO the entity to save
     * @return the persisted entity
     */
    SocialFollowDTO save(SocialFollowDTO socialFollowDTO);

    /**
     *  Get all the socialFollows.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SocialFollowDTO> findAll(Pageable pageable);
    
    List<SocialFollowDTO> findAll();

    /**
     *  Get the "id" socialFollow.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SocialFollowDTO findOne(Long id);

    /**
     *  Delete the "id" socialFollow.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
    
    List<SocialFollowDTO> getFollowers(String providerId, String followUserId);
    
    List<SocialFollowDTO> getFollowing(String providerId, String providerUserId);
    
    SocialFollowDTO getFollowing(String providerId, String providerUserId, String followUserId);
}

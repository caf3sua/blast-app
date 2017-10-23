package com.blast.service;

import com.blast.service.dto.KeywordUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing KeywordUser.
 */
public interface KeywordUserService {

    /**
     * Save a keywordUser.
     *
     * @param keywordUserDTO the entity to save
     * @return the persisted entity
     */
    KeywordUserDTO save(KeywordUserDTO keywordUserDTO);

    /**
     *  Get all the keywordUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<KeywordUserDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" keywordUser.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    KeywordUserDTO findOne(Long id);

    /**
     *  Delete the "id" keywordUser.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

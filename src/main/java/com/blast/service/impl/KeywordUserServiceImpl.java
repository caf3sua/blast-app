package com.blast.service.impl;

import com.blast.service.KeywordUserService;
import com.blast.domain.KeywordUser;
import com.blast.repository.KeywordUserRepository;
import com.blast.service.dto.KeywordUserDTO;
import com.blast.service.mapper.KeywordUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing KeywordUser.
 */
@Service
@Transactional
public class KeywordUserServiceImpl implements KeywordUserService{

    private final Logger log = LoggerFactory.getLogger(KeywordUserServiceImpl.class);

    private final KeywordUserRepository keywordUserRepository;

    private final KeywordUserMapper keywordUserMapper;

    public KeywordUserServiceImpl(KeywordUserRepository keywordUserRepository, KeywordUserMapper keywordUserMapper) {
        this.keywordUserRepository = keywordUserRepository;
        this.keywordUserMapper = keywordUserMapper;
    }

    /**
     * Save a keywordUser.
     *
     * @param keywordUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KeywordUserDTO save(KeywordUserDTO keywordUserDTO) {
        log.debug("Request to save KeywordUser : {}", keywordUserDTO);
        KeywordUser keywordUser = keywordUserMapper.toEntity(keywordUserDTO);
        keywordUser = keywordUserRepository.save(keywordUser);
        return keywordUserMapper.toDto(keywordUser);
    }

    /**
     *  Get all the keywordUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KeywordUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all KeywordUsers");
        return keywordUserRepository.findAll(pageable)
            .map(keywordUserMapper::toDto);
    }

    /**
     *  Get one keywordUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public KeywordUserDTO findOne(Long id) {
        log.debug("Request to get KeywordUser : {}", id);
        KeywordUser keywordUser = keywordUserRepository.findOne(id);
        return keywordUserMapper.toDto(keywordUser);
    }

    /**
     *  Delete the  keywordUser by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete KeywordUser : {}", id);
        keywordUserRepository.delete(id);
    }
}

package com.blast.service.impl;

import com.blast.service.KeywordService;
import com.blast.domain.Keyword;
import com.blast.repository.KeywordRepository;
import com.blast.service.dto.KeywordDTO;
import com.blast.service.mapper.KeywordMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Keyword.
 */
@Service
@Transactional
public class KeywordServiceImpl implements KeywordService{

    private final Logger log = LoggerFactory.getLogger(KeywordServiceImpl.class);

    private final KeywordRepository keywordRepository;

    private final KeywordMapper keywordMapper;

    public KeywordServiceImpl(KeywordRepository keywordRepository, KeywordMapper keywordMapper) {
        this.keywordRepository = keywordRepository;
        this.keywordMapper = keywordMapper;
    }

    /**
     * Save a keyword.
     *
     * @param keywordDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KeywordDTO save(KeywordDTO keywordDTO) {
        log.debug("Request to save Keyword : {}", keywordDTO);
        Keyword keyword = keywordMapper.toEntity(keywordDTO);
        keyword = keywordRepository.save(keyword);
        return keywordMapper.toDto(keyword);
    }

    /**
     *  Get all the keywords.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KeywordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Keywords");
        return keywordRepository.findAll(pageable)
            .map(keywordMapper::toDto);
    }

    /**
     *  Get one keyword by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public KeywordDTO findOne(Long id) {
        log.debug("Request to get Keyword : {}", id);
        Keyword keyword = keywordRepository.findOne(id);
        return keywordMapper.toDto(keyword);
    }

    /**
     *  Delete the  keyword by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Keyword : {}", id);
        keywordRepository.delete(id);
    }

	@Override
	public KeywordDTO updateStatus(String keyword, Integer status) {
		Keyword e = null;
		// Find by keyword
		e = keywordRepository.findOneByName(keyword);
		// exit -> update, else create new
		if (e != null) {
			if (status == 0) {
				long number = e.getNumberHate() + 1;
				e.setNumberHate(number);
			} else {
				long number = e.getNumberLike() + 1;
				e.setNumberLike(number);
			}
		} else {
			// Create new
			e = new Keyword();
			e.setName(keyword);
			if (status == 0) {
				e.setNumberHate(1l);
				e.setNumberLike(0l);
			} else {
				e.setNumberLike(1l);
				e.setNumberHate(0l);
			}
		}

		Keyword result = keywordRepository.save(e);
		return keywordMapper.toDto(result);
	}

	@Override
	public KeywordDTO findOneByName(String name) {
		log.debug("Request to get Keyword by name: {}", name);
        Keyword keyword = keywordRepository.findOneByName(name);
        return keywordMapper.toDto(keyword);
	}

	@Override
	public Page<KeywordDTO> findTopKeywordOfFriend(List<Long> lstUserId, Integer status, Pageable pageable) {
		log.debug("Request to findTopKeywordOfFriend: {} {}", lstUserId, status);
        Page<Keyword> keywords = keywordRepository.findTopKeywordOfFriend(lstUserId, status, pageable);
        return keywords.map(keywordMapper::toDto);
	}

	@Override
	public Page<KeywordDTO> findTopKeywordOfAll(Integer status, Pageable pageable) {
		log.debug("Request to findTopKeywordOfAll: {}", status);
        Page<Keyword> keywords = keywordRepository.findTopKeywordOfAll(status, pageable);
        return keywords.map(keywordMapper::toDto);
	}

	@Override
	public List<Long> findLatestUserIdByKeyword(Integer status, String keyword) {
		log.debug("Request to findLatestUserIdByKeyword {}", keyword);
		List<Long> lstUserId = keywordRepository.findLatestUserIdByKeyword(5, status, keyword);
		return lstUserId;
	}
}

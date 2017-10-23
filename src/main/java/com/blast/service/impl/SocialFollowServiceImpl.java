package com.blast.service.impl;

import com.blast.service.SocialFollowService;
import com.blast.domain.SocialFollow;
import com.blast.repository.SocialFollowRepository;
import com.blast.service.dto.SocialFollowDTO;
import com.blast.service.mapper.SocialFollowMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SocialFollow.
 */
@Service
@Transactional
public class SocialFollowServiceImpl implements SocialFollowService{

    private final Logger log = LoggerFactory.getLogger(SocialFollowServiceImpl.class);

    private final SocialFollowRepository socialFollowRepository;

    private final SocialFollowMapper socialFollowMapper;

    public SocialFollowServiceImpl(SocialFollowRepository socialFollowRepository, SocialFollowMapper socialFollowMapper) {
        this.socialFollowRepository = socialFollowRepository;
        this.socialFollowMapper = socialFollowMapper;
    }

    /**
     * Save a socialFollow.
     *
     * @param socialFollowDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SocialFollowDTO save(SocialFollowDTO socialFollowDTO) {
        log.debug("Request to save SocialFollow : {}", socialFollowDTO);
        SocialFollow socialFollow = socialFollowMapper.toEntity(socialFollowDTO);
        socialFollow = socialFollowRepository.save(socialFollow);
        return socialFollowMapper.toDto(socialFollow);
    }

    /**
     *  Get all the socialFollows.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SocialFollowDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SocialFollows");
        return socialFollowRepository.findAll(pageable)
            .map(socialFollowMapper::toDto);
    }

    /**
     *  Get one socialFollow by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SocialFollowDTO findOne(Long id) {
        log.debug("Request to get SocialFollow : {}", id);
        SocialFollow socialFollow = socialFollowRepository.findOne(id);
        return socialFollowMapper.toDto(socialFollow);
    }

    /**
     *  Delete the  socialFollow by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SocialFollow : {}", id);
        socialFollowRepository.delete(id);
    }

	@Override
	public List<SocialFollowDTO> findAll() {
		log.debug("Request to findAll SocialFollow");
        return socialFollowMapper.toDto(socialFollowRepository.findAll());
	}

	@Override
	public List<SocialFollowDTO> getFollowers(String userId, String followUserId) {
		log.debug("Request to getFollowers SocialFollow");
        return socialFollowMapper.toDto(socialFollowRepository.findAllByProviderIdAndFollowUserId(userId, followUserId));
	}

	@Override
	public List<SocialFollowDTO> getFollowing(String userId, String providerUserId) {
		log.debug("Request to getFollowing SocialFollow");
        return socialFollowMapper.toDto(socialFollowRepository.findAllByProviderIdAndProviderUserId(userId, providerUserId));
	}

	@Override
	public SocialFollowDTO getFollowing(String providerId, String providerUserId, String followUserId) {
		log.debug("Request to getFollowing SocialFollow");
        return socialFollowMapper.toDto(socialFollowRepository.findOneByProviderIdAndProviderUserIdAndFollowUserId(providerId, providerUserId, followUserId));
	}
}

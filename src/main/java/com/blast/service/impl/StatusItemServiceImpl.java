package com.blast.service.impl;

import com.blast.service.StatusItemService;
import com.blast.domain.StatusItem;
import com.blast.repository.StatusItemRepository;
import com.blast.service.dto.StatusItemDTO;
import com.blast.service.dto.TrendStatusDTO;
import com.blast.service.mapper.StatusItemMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing StatusItem.
 */
@Service
@Transactional
public class StatusItemServiceImpl implements StatusItemService{

    private final Logger log = LoggerFactory.getLogger(StatusItemServiceImpl.class);

    private final StatusItemRepository statusItemRepository;

    private final StatusItemMapper statusItemMapper;

    public StatusItemServiceImpl(StatusItemRepository statusItemRepository, StatusItemMapper statusItemMapper) {
        this.statusItemRepository = statusItemRepository;
        this.statusItemMapper = statusItemMapper;
    }

    /**
     * Save a statusItem.
     *
     * @param statusItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StatusItemDTO save(StatusItemDTO statusItemDTO) {
        log.debug("Request to save StatusItem : {}", statusItemDTO);
        StatusItem statusItem = statusItemMapper.toEntity(statusItemDTO);
        statusItem = statusItemRepository.save(statusItem);
        return statusItemMapper.toDto(statusItem);
    }

    /**
     *  Get all the statusItems.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StatusItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StatusItems");
        return statusItemRepository.findAll(pageable)
            .map(statusItemMapper::toDto);
    }

    /**
     *  Get one statusItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StatusItemDTO findOne(Long id) {
        log.debug("Request to get StatusItem : {}", id);
        StatusItem statusItem = statusItemRepository.findOne(id);
        return statusItemMapper.toDto(statusItem);
    }

    /**
     *  Delete the  statusItem by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StatusItem : {}", id);
        statusItemRepository.delete(id);
    }

	@Override
	public Page<StatusItemDTO> findByUserIdAndStatus(Long userId, Integer status, Pageable pageable) {
		log.debug("Request to findByUserIdAndStatus");
        return statusItemRepository.findByUserIdAndStatus(userId, status, pageable)
            .map(statusItemMapper::toDto);
	}

	@Override
	public List<TrendStatusDTO> trendOwner(Long userId, String fromDate, String toDate) {
		log.debug("Request to trendOwner, {} {} {}", userId, fromDate, toDate);
		return statusItemRepository.countByUserIdAndCreatedDateRanger(userId, fromDate, toDate);
	}
	
	@Override
	public List<TrendStatusDTO> trendFriend(Long userId, String fromDate, String toDate) {
		log.debug("Request to trendFriend, {} {} {}", userId, fromDate, toDate);
		return statusItemRepository.countByItemIdAndCreatedDateRanger(userId, fromDate, toDate);
	}

	@Override
	public List<StatusItemDTO> findByItemIdAndStatus(Long itemId, Integer status) {
		log.debug("Request to findByItemIdAndStatus, {} {}", itemId, status);
        return statusItemMapper.toDto(statusItemRepository.findByItemIdAndStatus(itemId, status));
	}

	@Override
	public List<StatusItemDTO> findByItemIdAndUserId(Long itemId, Long userId) {
		log.debug("Request to findByItemIdAndUserId, {} {} ", itemId, userId);
		return statusItemMapper.toDto(statusItemRepository.findByItemIdAndUserId(itemId, userId));
	}
}

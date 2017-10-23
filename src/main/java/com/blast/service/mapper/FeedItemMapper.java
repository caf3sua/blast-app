package com.blast.service.mapper;

import com.blast.domain.*;
import com.blast.service.dto.FeedItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FeedItem and its DTO FeedItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FeedItemMapper extends EntityMapper <FeedItemDTO, FeedItem> {
    
    
    default FeedItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        FeedItem feedItem = new FeedItem();
        feedItem.setId(id);
        return feedItem;
    }
}

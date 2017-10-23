package com.blast.service.mapper;

import com.blast.domain.*;
import com.blast.service.dto.StatusItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StatusItem and its DTO StatusItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StatusItemMapper extends EntityMapper <StatusItemDTO, StatusItem> {
    
    
    default StatusItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        StatusItem statusItem = new StatusItem();
        statusItem.setId(id);
        return statusItem;
    }
}

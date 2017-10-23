package com.blast.service.mapper;

import com.blast.domain.*;
import com.blast.service.dto.TrendDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Trend and its DTO TrendDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TrendMapper extends EntityMapper <TrendDTO, Trend> {
    
    
    default Trend fromId(Long id) {
        if (id == null) {
            return null;
        }
        Trend trend = new Trend();
        trend.setId(id);
        return trend;
    }
}

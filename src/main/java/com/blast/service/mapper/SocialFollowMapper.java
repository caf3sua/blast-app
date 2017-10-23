package com.blast.service.mapper;

import org.mapstruct.Mapper;

import com.blast.domain.SocialFollow;
import com.blast.service.dto.SocialFollowDTO;

/**
 * Mapper for the entity SocialFollow and its DTO SocialFollowDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SocialFollowMapper extends EntityMapper <SocialFollowDTO, SocialFollow> {
    
    
    default SocialFollow fromId(Long id) {
        if (id == null) {
            return null;
        }
        SocialFollow socialFollow = new SocialFollow();
        socialFollow.setId(id);
        return socialFollow;
    }
}


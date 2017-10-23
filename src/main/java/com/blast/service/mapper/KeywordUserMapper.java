package com.blast.service.mapper;

import com.blast.domain.*;
import com.blast.service.dto.KeywordUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity KeywordUser and its DTO KeywordUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface KeywordUserMapper extends EntityMapper <KeywordUserDTO, KeywordUser> {
    
    
    default KeywordUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        KeywordUser keywordUser = new KeywordUser();
        keywordUser.setId(id);
        return keywordUser;
    }
}

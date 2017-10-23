package com.blast.service.mapper;

import com.blast.domain.KeywordUser;
import com.blast.service.dto.KeywordUserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-10-19T22:29:14+0700",
    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_101 (Oracle Corporation)"
)
@Component
public class KeywordUserMapperImpl implements KeywordUserMapper {

    @Override
    public KeywordUser toEntity(KeywordUserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        KeywordUser keywordUser = new KeywordUser();

        keywordUser.setId( dto.getId() );
        keywordUser.setKeywordId( dto.getKeywordId() );
        keywordUser.setUserId( dto.getUserId() );
        keywordUser.setUpdatedDate( dto.getUpdatedDate() );

        return keywordUser;
    }

    @Override
    public KeywordUserDTO toDto(KeywordUser entity) {
        if ( entity == null ) {
            return null;
        }

        KeywordUserDTO keywordUserDTO = new KeywordUserDTO();

        keywordUserDTO.setId( entity.getId() );
        keywordUserDTO.setKeywordId( entity.getKeywordId() );
        keywordUserDTO.setUserId( entity.getUserId() );
        keywordUserDTO.setUpdatedDate( entity.getUpdatedDate() );

        return keywordUserDTO;
    }

    @Override
    public List<KeywordUser> toEntity(List<KeywordUserDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<KeywordUser> list = new ArrayList<KeywordUser>();
        for ( KeywordUserDTO keywordUserDTO : dtoList ) {
            list.add( toEntity( keywordUserDTO ) );
        }

        return list;
    }

    @Override
    public List<KeywordUserDTO> toDto(List<KeywordUser> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<KeywordUserDTO> list = new ArrayList<KeywordUserDTO>();
        for ( KeywordUser keywordUser : entityList ) {
            list.add( toDto( keywordUser ) );
        }

        return list;
    }
}

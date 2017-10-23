package com.blast.service.mapper;

import com.blast.domain.Keyword;
import com.blast.service.dto.KeywordDTO;
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
public class KeywordMapperImpl implements KeywordMapper {

    @Override
    public Keyword toEntity(KeywordDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Keyword keyword = new Keyword();

        keyword.setNumberLike( dto.getNumberLike() );
        keyword.setNumberHate( dto.getNumberHate() );
        keyword.setMoreKeyword( dto.getMoreKeyword() );
        keyword.setId( dto.getId() );
        keyword.setName( dto.getName() );
        keyword.setFeedId( dto.getFeedId() );

        return keyword;
    }

    @Override
    public KeywordDTO toDto(Keyword entity) {
        if ( entity == null ) {
            return null;
        }

        KeywordDTO keywordDTO = new KeywordDTO();

        keywordDTO.setNumberLike( entity.getNumberLike() );
        keywordDTO.setNumberHate( entity.getNumberHate() );
        keywordDTO.setMoreKeyword( entity.getMoreKeyword() );
        keywordDTO.setId( entity.getId() );
        keywordDTO.setName( entity.getName() );
        keywordDTO.setFeedId( entity.getFeedId() );

        return keywordDTO;
    }

    @Override
    public List<Keyword> toEntity(List<KeywordDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Keyword> list = new ArrayList<Keyword>();
        for ( KeywordDTO keywordDTO : dtoList ) {
            list.add( toEntity( keywordDTO ) );
        }

        return list;
    }

    @Override
    public List<KeywordDTO> toDto(List<Keyword> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<KeywordDTO> list = new ArrayList<KeywordDTO>();
        for ( Keyword keyword : entityList ) {
            list.add( toDto( keyword ) );
        }

        return list;
    }
}

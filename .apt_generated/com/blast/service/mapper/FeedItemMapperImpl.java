package com.blast.service.mapper;

import com.blast.domain.FeedItem;
import com.blast.service.dto.FeedItemDTO;
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
public class FeedItemMapperImpl implements FeedItemMapper {

    @Override
    public FeedItem toEntity(FeedItemDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FeedItem feedItem = new FeedItem();

        feedItem.setImageThumbUrl( dto.getImageThumbUrl() );
        feedItem.setMainKeyword( dto.getMainKeyword() );
        feedItem.setFilename( dto.getFilename() );
        feedItem.setUserId( dto.getUserId() );
        feedItem.setId( dto.getId() );
        feedItem.setData( dto.getData() );
        feedItem.setImageUrl( dto.getImageUrl() );
        feedItem.setShare( dto.isShare() );
        feedItem.setStatus( dto.getStatus() );
        feedItem.setKeywords( dto.getKeywords() );

        return feedItem;
    }

    @Override
    public FeedItemDTO toDto(FeedItem entity) {
        if ( entity == null ) {
            return null;
        }

        FeedItemDTO feedItemDTO = new FeedItemDTO();

        feedItemDTO.setImageThumbUrl( entity.getImageThumbUrl() );
        feedItemDTO.setId( entity.getId() );
        feedItemDTO.setData( entity.getData() );
        feedItemDTO.setImageUrl( entity.getImageUrl() );
        feedItemDTO.setShare( entity.isShare() );
        feedItemDTO.setStatus( entity.getStatus() );
        feedItemDTO.setKeywords( entity.getKeywords() );
        feedItemDTO.setFilename( entity.getFilename() );
        feedItemDTO.setUserId( entity.getUserId() );
        feedItemDTO.setMainKeyword( entity.getMainKeyword() );

        return feedItemDTO;
    }

    @Override
    public List<FeedItem> toEntity(List<FeedItemDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FeedItem> list = new ArrayList<FeedItem>();
        for ( FeedItemDTO feedItemDTO : dtoList ) {
            list.add( toEntity( feedItemDTO ) );
        }

        return list;
    }

    @Override
    public List<FeedItemDTO> toDto(List<FeedItem> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FeedItemDTO> list = new ArrayList<FeedItemDTO>();
        for ( FeedItem feedItem : entityList ) {
            list.add( toDto( feedItem ) );
        }

        return list;
    }
}

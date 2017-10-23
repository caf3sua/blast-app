package com.blast.service.mapper;

import com.blast.domain.StatusItem;
import com.blast.service.dto.StatusItemDTO;
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
public class StatusItemMapperImpl implements StatusItemMapper {

    @Override
    public StatusItem toEntity(StatusItemDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StatusItem statusItem = new StatusItem();

        statusItem.setId( dto.getId() );
        statusItem.setItemId( dto.getItemId() );
        statusItem.setUserId( dto.getUserId() );
        statusItem.setStatus( dto.getStatus() );
        statusItem.setDescription( dto.getDescription() );
        statusItem.setCreatedDate( dto.getCreatedDate() );

        return statusItem;
    }

    @Override
    public StatusItemDTO toDto(StatusItem entity) {
        if ( entity == null ) {
            return null;
        }

        StatusItemDTO statusItemDTO = new StatusItemDTO();

        statusItemDTO.setId( entity.getId() );
        statusItemDTO.setItemId( entity.getItemId() );
        statusItemDTO.setUserId( entity.getUserId() );
        statusItemDTO.setStatus( entity.getStatus() );
        statusItemDTO.setDescription( entity.getDescription() );
        statusItemDTO.setCreatedDate( entity.getCreatedDate() );

        return statusItemDTO;
    }

    @Override
    public List<StatusItem> toEntity(List<StatusItemDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StatusItem> list = new ArrayList<StatusItem>();
        for ( StatusItemDTO statusItemDTO : dtoList ) {
            list.add( toEntity( statusItemDTO ) );
        }

        return list;
    }

    @Override
    public List<StatusItemDTO> toDto(List<StatusItem> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StatusItemDTO> list = new ArrayList<StatusItemDTO>();
        for ( StatusItem statusItem : entityList ) {
            list.add( toDto( statusItem ) );
        }

        return list;
    }
}

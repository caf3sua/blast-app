package com.blast.service.mapper;

import com.blast.domain.Trend;
import com.blast.service.dto.TrendDTO;
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
public class TrendMapperImpl implements TrendMapper {

    @Override
    public Trend toEntity(TrendDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Trend trend = new Trend();

        trend.setId( dto.getId() );
        trend.setName( dto.getName() );
        trend.setDescription( dto.getDescription() );
        trend.setInterval( dto.getInterval() );

        return trend;
    }

    @Override
    public TrendDTO toDto(Trend entity) {
        if ( entity == null ) {
            return null;
        }

        TrendDTO trendDTO = new TrendDTO();

        trendDTO.setId( entity.getId() );
        trendDTO.setName( entity.getName() );
        trendDTO.setDescription( entity.getDescription() );
        trendDTO.setInterval( entity.getInterval() );

        return trendDTO;
    }

    @Override
    public List<Trend> toEntity(List<TrendDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Trend> list = new ArrayList<Trend>();
        for ( TrendDTO trendDTO : dtoList ) {
            list.add( toEntity( trendDTO ) );
        }

        return list;
    }

    @Override
    public List<TrendDTO> toDto(List<Trend> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TrendDTO> list = new ArrayList<TrendDTO>();
        for ( Trend trend : entityList ) {
            list.add( toDto( trend ) );
        }

        return list;
    }
}

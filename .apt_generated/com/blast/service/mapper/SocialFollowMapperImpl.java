package com.blast.service.mapper;

import com.blast.domain.SocialFollow;
import com.blast.service.dto.SocialFollowDTO;
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
public class SocialFollowMapperImpl implements SocialFollowMapper {

    @Override
    public SocialFollow toEntity(SocialFollowDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SocialFollow socialFollow = new SocialFollow();

        socialFollow.setId( dto.getId() );
        socialFollow.setProviderId( dto.getProviderId() );
        socialFollow.setProviderUserId( dto.getProviderUserId() );
        socialFollow.setFollowUserId( dto.getFollowUserId() );

        return socialFollow;
    }

    @Override
    public SocialFollowDTO toDto(SocialFollow entity) {
        if ( entity == null ) {
            return null;
        }

        SocialFollowDTO socialFollowDTO = new SocialFollowDTO();

        socialFollowDTO.setId( entity.getId() );
        socialFollowDTO.setProviderId( entity.getProviderId() );
        socialFollowDTO.setProviderUserId( entity.getProviderUserId() );
        socialFollowDTO.setFollowUserId( entity.getFollowUserId() );

        return socialFollowDTO;
    }

    @Override
    public List<SocialFollow> toEntity(List<SocialFollowDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SocialFollow> list = new ArrayList<SocialFollow>();
        for ( SocialFollowDTO socialFollowDTO : dtoList ) {
            list.add( toEntity( socialFollowDTO ) );
        }

        return list;
    }

    @Override
    public List<SocialFollowDTO> toDto(List<SocialFollow> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SocialFollowDTO> list = new ArrayList<SocialFollowDTO>();
        for ( SocialFollow socialFollow : entityList ) {
            list.add( toDto( socialFollow ) );
        }

        return list;
    }
}

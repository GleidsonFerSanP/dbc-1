package br.com.dbccompany.core.mapper;

import br.com.dbccompany.core.domain.dto.PaltaDto;
import br.com.dbccompany.core.domain.entity.PaltaEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-29T14:20:01-0300",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.7 (Ubuntu)"
)
@Component
public class PaltaMapperImpl implements PaltaMapper {

    @Override
    public PaltaEntity toEntity(PaltaDto paltaDto) {
        if ( paltaDto == null ) {
            return null;
        }

        PaltaEntity paltaEntity = new PaltaEntity();

        paltaEntity.setTitle( paltaDto.getTitle() );
        paltaEntity.setDescription( paltaDto.getDescription() );

        return paltaEntity;
    }

    @Override
    public PaltaDto toDto(PaltaEntity paltaEntity) {
        if ( paltaEntity == null ) {
            return null;
        }

        PaltaDto paltaDto = new PaltaDto();

        paltaDto.setCode( getCode( paltaEntity.getCode() ) );
        paltaDto.setTitle( paltaEntity.getTitle() );
        paltaDto.setDescription( paltaEntity.getDescription() );

        return paltaDto;
    }
}

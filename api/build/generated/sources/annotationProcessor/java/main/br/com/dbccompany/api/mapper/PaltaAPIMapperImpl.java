package br.com.dbccompany.api.mapper;

import br.com.dbccompany.api.request.v1.PaltaRequest;
import br.com.dbccompany.api.response.v1.PaltaResponse;
import br.com.dbccompany.core.domain.dto.PaltaDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-29T11:13:21-0300",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.7 (Ubuntu)"
)
@Component
public class PaltaAPIMapperImpl implements PaltaAPIMapper {

    @Override
    public PaltaDto toDto(PaltaRequest request) {
        if ( request == null ) {
            return null;
        }

        PaltaDto paltaDto = new PaltaDto();

        return paltaDto;
    }

    @Override
    public PaltaResponse toResponse(PaltaDto paltaDto) {
        if ( paltaDto == null ) {
            return null;
        }

        PaltaResponse paltaResponse = new PaltaResponse();

        paltaResponse.setCode( paltaDto.getCode() );
        paltaResponse.setDescription( paltaDto.getDescription() );

        return paltaResponse;
    }
}

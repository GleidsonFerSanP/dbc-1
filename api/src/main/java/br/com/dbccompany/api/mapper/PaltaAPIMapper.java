package br.com.dbccompany.api.mapper;

import br.com.dbccompany.api.resource.request.v1.PaltaRequest;
import br.com.dbccompany.api.resource.response.v1.PaltaResponse;
import br.com.dbccompany.core.domain.dto.PaltaDto;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface PaltaAPIMapper {

    PaltaDto toDto(final PaltaRequest request);

    PaltaResponse toResponse(final PaltaDto paltaDto);
}

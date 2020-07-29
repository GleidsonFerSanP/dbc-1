package com.sicred.api.mapper;

import com.sicred.api.request.v1.PaltaRequest;
import com.sicred.api.response.v1.PaltaResponse;
import com.sicred.core.domain.dto.PaltaDto;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface PaltaAPIMapper {
    PaltaDto toDto(final PaltaRequest request);

    PaltaResponse toResponse(final PaltaDto paltaDto);
}

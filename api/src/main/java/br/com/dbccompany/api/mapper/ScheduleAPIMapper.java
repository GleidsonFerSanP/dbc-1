package br.com.dbccompany.api.mapper;

import br.com.dbccompany.api.resource.request.v1.ScheduleRequest;
import br.com.dbccompany.api.resource.response.v1.ScheduleResponse;
import br.com.dbccompany.core.domain.dto.ScheduleDto;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface ScheduleAPIMapper {

    ScheduleDto toDto(final ScheduleRequest request);

    ScheduleResponse toResponse(final ScheduleDto scheduleDto);
}

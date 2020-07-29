package br.com.dbccompany.core.mapper;

import br.com.dbccompany.core.domain.dto.ScheduleDto;
import br.com.dbccompany.core.domain.entity.ScheduleEntity;
import org.mapstruct.*;

import java.util.UUID;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface ScheduleMapper {

    ScheduleEntity toEntity(final ScheduleDto scheduleDto);

    @Mappings({
            @Mapping(target = "expiration", ignore = true)
    })
    void updateEntity(final ScheduleDto scheduleDto, @MappingTarget final ScheduleEntity existsScheduleEntity);

    @Mapping(target = "code", source = "code", qualifiedByName = "getCode")
    ScheduleDto toDto(final ScheduleEntity scheduleEntity);

    @Named("getCode")
    default String getCode(final UUID code){
        return code.toString();
    }
}

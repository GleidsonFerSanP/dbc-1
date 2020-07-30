package br.com.dbccompany.core.mapper;

import br.com.dbccompany.core.domain.dto.VoteDto;
import br.com.dbccompany.core.domain.entity.ScheduleEntity;
import br.com.dbccompany.core.domain.entity.VoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface VoteMapper {

    @Mapping(target = "schedule", source = "scheduleCode", qualifiedByName = "toScheduleEntity")
    VoteEntity toEntity(final VoteDto voteDto);

    @Mapping(target = "scheduleCode", source = "schedule", qualifiedByName = "toScheduleCode")
    VoteDto toDto(final VoteEntity voteEntity);

    @Named("toScheduleEntity")
    default ScheduleEntity toScheduleEntity(final UUID scheduleCode){
        return new ScheduleEntity(scheduleCode);
    }

    @Named("toScheduleCode")
    default UUID toScheduleCode(final ScheduleEntity scheduleEntity){
        return scheduleEntity.getCode();
    }
}

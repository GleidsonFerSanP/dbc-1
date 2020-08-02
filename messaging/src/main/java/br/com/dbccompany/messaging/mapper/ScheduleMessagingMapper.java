package br.com.dbccompany.messaging.mapper;

import br.com.dbccompany.core.domain.dto.ScheduleResultDto;
import br.com.dbccompany.core.domain.message.ScheduleResultMessage;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface ScheduleMessagingMapper {

    ScheduleResultMessage toMessage(final ScheduleResultDto scheduleResultDto);

}

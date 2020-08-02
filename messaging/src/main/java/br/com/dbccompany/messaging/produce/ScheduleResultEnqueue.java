package br.com.dbccompany.messaging.produce;

import br.com.dbccompany.core.service.ScheduleFindService;
import br.com.dbccompany.messaging.mapper.ScheduleMessagingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleResultEnqueue {

    private static final String SCHEDULE_RESULT_TOPIC = "schedule.result";

    private final KafkaTemplate<String,Object> kafkaTemplate;

    private final ScheduleFindService scheduleFindService;

    private final ScheduleMessagingMapper scheduleMessagingMapper;

    public void send(final String scheduleCode) {
        log.info("I= init send event, TAG=SCHEDULE_RESULT_TOPIC scheduleCode={}", scheduleCode);
        final var scheduleResultDto = scheduleFindService.findResultsByCode(scheduleCode);
        final var scheduleResultMessage = scheduleMessagingMapper.toMessage(scheduleResultDto);
        kafkaTemplate.send(SCHEDULE_RESULT_TOPIC, scheduleResultMessage);
        log.info("I=send event, TAG=SCHEDULE_RESULT_TOPIC scheduleCode={}, " +
                "scheduleResultMessage={}", scheduleCode, scheduleResultMessage);
    }
}

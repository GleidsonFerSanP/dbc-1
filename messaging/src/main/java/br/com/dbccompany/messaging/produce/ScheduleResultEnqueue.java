package br.com.dbccompany.messaging.produce;

import br.com.dbccompany.core.service.VoteFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleResultEnqueue {

    private static final String SCHEDULE_RESULT_TOPIC = "schedule.result";

    private final KafkaTemplate<String,String> kafkaTemplate;

    private final VoteFindService voteFindService;

    public void send(final String scheduleCode) {
        log.info("I=send event, TAG=SCHEDULE_RESULT_TOPIC scheduleCode={}", scheduleCode);

        voteFindService.findByScheduleCodeGroupByVoteOption(scheduleCode);
        kafkaTemplate.send(SCHEDULE_RESULT_TOPIC, scheduleCode);
    }
}

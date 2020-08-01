package br.com.dbccompany.messaging.produce;

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

    public void send(final String message) {
        log.info("I=send event, TAG=SCHEDULE_RESULT_TOPIC message={}", message);
        kafkaTemplate.send(SCHEDULE_RESULT_TOPIC, message);
    }
}

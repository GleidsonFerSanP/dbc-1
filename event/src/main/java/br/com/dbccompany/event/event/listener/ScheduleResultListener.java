package br.com.dbccompany.event.event.listener;

import br.com.dbccompany.messaging.annotation.Listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Listener
public class ScheduleResultListener {

    private static final String SCHEDULE_RESULT_TOPIC = "schedule.result";

    @KafkaListener(topics = SCHEDULE_RESULT_TOPIC)
    public void receive(final String message) {
        log.info("I=consuming event, TAG=SCHEDULE_RESULT_TOPIC message={}", message);
    }
}

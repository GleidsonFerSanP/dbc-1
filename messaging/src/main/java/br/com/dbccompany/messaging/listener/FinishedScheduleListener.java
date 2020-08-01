package br.com.dbccompany.messaging.listener;

import br.com.dbccompany.messaging.annotation.Listener;
import br.com.dbccompany.messaging.produce.ScheduleResultEnqueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Slf4j
@Listener
@RequiredArgsConstructor
public class FinishedScheduleListener {

    private static final String FINISHED_SCHEDULE_QUEUE = "finished-schedule-queue";

    private final ScheduleResultEnqueue scheduleResultEnqueue;

    @RabbitListener(queues = FINISHED_SCHEDULE_QUEUE)
    public void consume(final String code) {
        log.info("I=consuming event, TAG=CONSUMING_FINISHED_SCHEDULE_QUEUE code={}", code);
        scheduleResultEnqueue.send(code);
    }
}

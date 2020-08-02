package br.com.dbccompany.core.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleFinishNotifyEnqueue {

    private static final String EXCHANGE_NAME = "schedule-exchange";

    private static final String ROUTING_KEY = "createdScheduleRoutingKey";

    private final RabbitTemplate rabbitTemplate;

    public void send(final String message, final Integer ttl) {
        log.info("I=send, message={}, exchange={}, ttl={}", message, EXCHANGE_NAME, ttl);

        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties()
                        .setExpiration(convertMinutesInMilliseconds(ttl).toString());
                return message;
            }
            private Long convertMinutesInMilliseconds(final Integer ttl) {
                return (long) (60000 * ttl);
            }
        };
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message, messagePostProcessor);
        log.info("I=success send, message={}, exchange={}, ttl={}", message, EXCHANGE_NAME, ttl);
    }
}

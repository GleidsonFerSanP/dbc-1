package br.com.dbccompany.core.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

public class ExpirationMessagePostProcessor implements MessagePostProcessor {

    private final Long ttl;

    public ExpirationMessagePostProcessor(Integer timeInMinutes) {
        this.ttl = convertMinutesInMilliseconds(timeInMinutes);
    }

    private Long convertMinutesInMilliseconds(final Integer ttl) {
        return (long) (60000 * ttl);
    }

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties()
                .setExpiration(ttl.toString());
        return message;
    }
}

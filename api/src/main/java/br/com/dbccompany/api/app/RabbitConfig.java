package br.com.dbccompany.api.app;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private static final String EXCHANGE_NAME = "schedule-exchange";

    public static final String CREATED_SCHEDULE_QUEUE = "created-schedule-queue";

    private static final String CREATED_SCHECULE_ROUTING_KEY = "createdScheduleRoutingKey";
    
    private static final String FINISHED_SCHEDULE_QUEUE = "finished-schedule-queue";

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue finishedScheduleQueue() {
        return QueueBuilder.durable(FINISHED_SCHEDULE_QUEUE)
                .deadLetterExchange(EXCHANGE_NAME)
                .deadLetterRoutingKey(CREATED_SCHEDULE_QUEUE)
                .build();
    }

    @Bean
    Queue createdScheduleQueue() {
        return QueueBuilder.durable(CREATED_SCHEDULE_QUEUE)
                .deadLetterExchange(EXCHANGE_NAME)
                .deadLetterRoutingKey(CREATED_SCHECULE_ROUTING_KEY)
                .build();
    }

    @Bean
    Binding finishedScheduleBinding(Queue finishedScheduleQueue, DirectExchange exchange) {
        return BindingBuilder.bind(finishedScheduleQueue).to(exchange).with(CREATED_SCHECULE_ROUTING_KEY);
    }

    @Bean
    Binding createdScheduleBinding(Queue createdScheduleQueue, DirectExchange exchange){
        return BindingBuilder.bind(createdScheduleQueue).to(exchange).with(CREATED_SCHECULE_ROUTING_KEY);
    }
}

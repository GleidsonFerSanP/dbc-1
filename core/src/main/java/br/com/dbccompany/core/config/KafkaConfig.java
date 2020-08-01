package br.com.dbccompany.messaging.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    private static final String TOPIC_SCHEDULE_CREATED = "schedule.created";

    @Bean
    public NewTopic topicExample() {
        return TopicBuilder.name(TOPIC_SCHEDULE_CREATED)
                .partitions(3)
                .replicas(1)
                .build();
    }
}

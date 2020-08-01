package br.com.dbccompany.core.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    private static final String TOPIC_SCHEDULE_CREATED = "schedule.result";

    @Bean
    public NewTopic sheduleResultTopic() {
        return TopicBuilder.name(TOPIC_SCHEDULE_CREATED)
                .partitions(3)
                .replicas(1)
                .build();
    }
}

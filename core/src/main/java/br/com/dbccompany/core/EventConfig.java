package br.com.dbccompany.core;

import br.com.dbccompany.core.config.KafkaConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(KafkaConfig.class)
public class EventConfig {
}

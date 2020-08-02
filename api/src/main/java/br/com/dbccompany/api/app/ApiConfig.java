package br.com.dbccompany.api.app;

import br.com.dbccompany.core.CoreConfig;
import br.com.dbccompany.core.config.FeignConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackageClasses = ApiConfig.class)
@Import({ CoreConfig.class, FeignConfig.class })
@EnableAutoConfiguration(exclude = KafkaAutoConfiguration.class)
public class ApiConfig {
}

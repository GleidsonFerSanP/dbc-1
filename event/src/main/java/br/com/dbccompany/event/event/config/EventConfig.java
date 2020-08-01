package br.com.dbccompany.event.event.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(br.com.dbccompany.core.EventConfig.class)
@ComponentScan(basePackageClasses = EventConfig.class)
public class EventConfig {
}

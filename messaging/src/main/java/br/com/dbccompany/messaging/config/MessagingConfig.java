package br.com.dbccompany.messaging.config;

import br.com.dbccompany.core.CoreConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CoreConfig.class)
@ComponentScan(basePackageClasses = MessagingConfig.class)
public class MessagingConfig {
}

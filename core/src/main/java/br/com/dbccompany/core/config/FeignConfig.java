package br.com.dbccompany.core.config;

import br.com.dbccompany.core.integration.client.HubDevClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackageClasses = HubDevClient.class)
@Configuration
public class FeignConfig {
}

package com.sicred.api.app;

import com.sicred.core.CoreConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackageClasses = ApiConfig.class)
@Import(CoreConfig.class)
public class ApiConfig {
}

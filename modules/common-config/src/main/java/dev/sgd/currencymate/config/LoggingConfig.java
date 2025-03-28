package dev.sgd.currencymate.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

    @Bean
    public Logger feignLogger() {
        return LoggerFactory.getLogger("feign.client");
    }

}
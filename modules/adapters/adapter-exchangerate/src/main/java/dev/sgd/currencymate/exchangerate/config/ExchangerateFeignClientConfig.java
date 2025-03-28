package dev.sgd.currencymate.exchangerate.config;

import feign.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableFeignClients(basePackages = "dev.sgd.currencymate.exchangerate.client")
public class ExchangerateFeignClientConfig {

    @Value("${app.adapter.exchangerate.connectTimeoutMs}")
    private int connectTimeoutMillis;

    @Value("${app.adapter.exchangerate.readTimeoutMs}")
    private int readTimeoutMillis;

    @Bean
    public Logger feignLogger() {
        return LoggerFactory.getLogger("feign.client");
    }

    @Bean
    public Request.Options exchangerateOptions() {
        return new Request.Options(
                connectTimeoutMillis, TimeUnit.MILLISECONDS,
                readTimeoutMillis, TimeUnit.MILLISECONDS,
                true);
    }

}
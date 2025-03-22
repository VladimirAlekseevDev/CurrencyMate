package dev.sgd.currencymate.adapteralphavantage.config;

import dev.sgd.currencymate.domain.error.AdapterException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "dev.sgd.currencymate.adapteralphavantage.client")
public class AlphavantageFeignClientConfig {

    @Bean
    public Logger feignLogger() {
        return LoggerFactory.getLogger("feign.client");
    }

    @Bean
    public ErrorDecoder errorDecoder(Logger feignLogger) {
        return (methodKey, response) -> {
            feignLogger.error("Error in adapter method '{}': status: {}, response body: {}",
                methodKey,
                response.status(),
                response.body() != null ? response.body().toString() : "null");

            throw new AdapterException();
        };
    }

    @Bean
    public Retryer feignRetryer(@Value("${app.adapter.alphavantage.retry.attempts}") int maxAttempts,
                                @Value("${app.adapter.alphavantage.retry.delay}") long delay) {
        return new Retryer.Default(delay, delay * 2, maxAttempts);
    }

}
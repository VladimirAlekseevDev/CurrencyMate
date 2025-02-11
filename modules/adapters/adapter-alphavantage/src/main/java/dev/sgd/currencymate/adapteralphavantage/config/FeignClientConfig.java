package dev.sgd.currencymate.adapteralphavantage.config;

import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "dev.sgd.currencymate.adapteralphavantage.client")
public class FeignClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            if (response.status() == 404) {
                return new RuntimeException("Resource not found");
            }
            return new RuntimeException("Generic server error");
        };
    }

}
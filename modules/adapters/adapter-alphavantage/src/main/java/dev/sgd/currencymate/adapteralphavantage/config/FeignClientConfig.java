package dev.sgd.currencymate.adapteralphavantage.config;

import dev.sgd.currencymate.domain.error.AdapterException;
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
            // TODO add logging
            //  log.error("Error in adapter method '{}': status: {}, response body: {}",
            //  methodKey, response.status(), response.body().toString())
            throw new AdapterException();
        };
    }

}
package dev.sgd.currencymate.exchangerate;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sgd.currencymate.exchangerate.client.ExchangerateClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MockExchangerateConfig {

    @Bean
    @Primary
    public ExchangerateClient exchangerateClient(ObjectMapper objectMapper) {
        return new MockExchangerateClient(objectMapper);
    }

}
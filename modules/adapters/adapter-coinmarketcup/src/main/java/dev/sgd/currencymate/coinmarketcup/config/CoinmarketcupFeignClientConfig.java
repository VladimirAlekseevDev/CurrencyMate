package dev.sgd.currencymate.coinmarketcup.config;

import feign.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableFeignClients(basePackages = "dev.sgd.currencymate.coinmarketcup.client")
public class CoinmarketcupFeignClientConfig {

    @Value("${app.adapter.coinmarketcap.connectTimeoutMs}")
    private int connectTimeoutMillis;

    @Value("${app.adapter.coinmarketcap.readTimeoutMs}")
    private int readTimeoutMillis;

    @Bean
    public Request.Options coinmarketcupOptions() {
        return new Request.Options(
                connectTimeoutMillis, TimeUnit.MILLISECONDS,
                readTimeoutMillis, TimeUnit.MILLISECONDS,
                true);
    }

}
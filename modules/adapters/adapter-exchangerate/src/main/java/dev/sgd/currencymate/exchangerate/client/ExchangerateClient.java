package dev.sgd.currencymate.exchangerate.client;

import dev.sgd.currencymate.alphavantage.model.exchangerate.ExchangeRateResponse;
import dev.sgd.currencymate.exchangerate.config.ExchangerateFeignClientConfig;
import dev.sgd.currencymate.exchangerate.config.ExchangerateFeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * <a href="https://www.alphavantage.co/documentation/">API Reference</a>
 */
@FeignClient(
    name = "exchangerate-client",
    url = "${app.adapter.exchangerate.url}",
    configuration = { ExchangerateFeignClientConfig.class, ExchangerateFeignErrorDecoder.class })
public interface ExchangerateClient {

    /**
     * <a href="https://www.exchangerate-api.com/docs/pair-conversion-requests">
     *     Documentation for the Pair ExchangeRate-API endpoint
     * </a>
     */
    @GetMapping("/pair/{from_currency}/{to_currency}")
    ExchangeRateResponse getExchangeRate(
        @RequestHeader(AUTHORIZATION) String apiKey,
        @RequestParam("from_currency") String fromCurrency,
        @RequestParam("to_currency") String toCurrency);

}
package dev.sgd.currencymate.exchangerate.client;

import dev.sgd.currencymate.exchangerate.config.ExchangerateFeignClientConfig;
import dev.sgd.currencymate.exchangerate.config.ExchangerateFeignErrorDecoder;
import dev.sgd.currencymate.exchangerate.model.AllCurrenciesResponse;
import dev.sgd.currencymate.exchangerate.model.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

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
     * <a href="https://www.exchangerate-api.com/docs/supported-codes-endpoint">
     *     Documentation for the Supported Codes ExchangeRate-API endpoint
     * </a>
     */
    @GetMapping("/codes")
    AllCurrenciesResponse getAllCurrencies(@RequestHeader(AUTHORIZATION) String apiKey);

    /**
     * <a href="https://www.exchangerate-api.com/docs/pair-conversion-requests">
     *     Documentation for the Pair ExchangeRate-API endpoint
     * </a>
     */
    @GetMapping("/pair/{from_currency}/{to_currency}")
    ExchangeRateResponse getExchangeRate(
            @RequestHeader(AUTHORIZATION) String apiKey,
            @PathVariable("from_currency") String fromCurrency,
            @PathVariable("to_currency") String toCurrency);

}
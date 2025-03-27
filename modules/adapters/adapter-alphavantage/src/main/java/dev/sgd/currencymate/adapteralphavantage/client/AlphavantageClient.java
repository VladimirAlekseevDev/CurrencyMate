package dev.sgd.currencymate.adapteralphavantage.client;

import dev.sgd.currencymate.adapteralphavantage.config.AlphavantageFeignClientConfig;
import dev.sgd.currencymate.adapteralphavantage.config.AlphavantageFeignErrorDecoder;
import dev.sgd.currencymate.adapteralphavantage.model.exchangerate.ExchangeRateResponse;
import dev.sgd.currencymate.adapteralphavantage.model.timeseries.DailyExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <a href="https://www.alphavantage.co/documentation/">API Reference</a>
 */
@FeignClient(
    name = "alphavantage-client",
    url = "${app.adapter.alphavantage.url}",
    configuration = { AlphavantageFeignClientConfig.class, AlphavantageFeignErrorDecoder.class })
public interface AlphavantageClient {

    /**
     * <a href="https://www.alphavantage.co/documentation/#currency-exchange">Currency Exchange API Reference</a>
     */
    @GetMapping("/query")
    ExchangeRateResponse getExchangeRate(
        @RequestParam("function") String function,
        @RequestParam("from_currency") String fromCurrency,
        @RequestParam("to_currency") String toCurrency,
        @RequestParam("apikey") String apiKey);

    /**
     * <a href="https://www.alphavantage.co/documentation/#fx-daily">FX Daily API Reference</a>
     */
    @GetMapping("/query")
    DailyExchangeRateResponse getExchangeRateDaily(
        @RequestParam("function") String function,
        @RequestParam("from_symbol") String fromSymbol,
        @RequestParam("to_symbol") String toSymbol,
        @RequestParam("outputsize") String outputSize, // compact (100 points) or full
        @RequestParam("apikey") String apiKey
    );

}
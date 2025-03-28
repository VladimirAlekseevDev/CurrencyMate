package dev.sgd.currencymate.alphavantage.client;

import dev.sgd.currencymate.alphavantage.config.AlphavantageFeignClientConfig;
import dev.sgd.currencymate.alphavantage.config.AlphavantageFeignErrorDecoder;
import dev.sgd.currencymate.alphavantage.model.exchangerate.ExchangeRateResponse;
import dev.sgd.currencymate.alphavantage.model.daily.DailyExchangeRateResponse;
import dev.sgd.currencymate.alphavantage.model.weekly.WeeklyExchangeRateResponse;
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
     * @param outputSize compact / full
     */
    @GetMapping("/query")
    DailyExchangeRateResponse getDailyExchangeRate(
        @RequestParam("function") String function,
        @RequestParam("from_symbol") String fromSymbol,
        @RequestParam("to_symbol") String toSymbol,
        @RequestParam("outputsize") String outputSize,
        @RequestParam("apikey") String apiKey
    );

    /**
     * <a href="https://www.alphavantage.co/documentation/#fx-daily">FX Daily API Reference</a>
     * @param outputSize compact / full
     */
    @GetMapping("/query")
    WeeklyExchangeRateResponse getWeeklyExchangeRate(
            @RequestParam("function") String function,
            @RequestParam("from_symbol") String fromSymbol,
            @RequestParam("to_symbol") String toSymbol,
            @RequestParam("apikey") String apiKey
    );

}
package dev.sgd.currencymate.coinmarketcup.client;

import dev.sgd.currencymate.coinmarketcup.config.CoinmarketcupFeignClientConfig;
import dev.sgd.currencymate.coinmarketcup.config.CoinmarketcupFeignErrorDecoder;
import dev.sgd.currencymate.coinmarketcup.model.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <a href="https://coinmarketcap.com/api/documentation/">API Reference</a>
 */
@FeignClient(
        name = "coinmarketcup-client",
        url = "${app.adapter.coinmarketcap.url}",
        configuration = { CoinmarketcupFeignClientConfig.class, CoinmarketcupFeignErrorDecoder.class })
public interface CoinmarketcupClient {

    String AUTH_HEADER = "X-CMC_PRO_API_KEY";

    /**
     * <a href="https://coinmarketcap.com/api/documentation/v1/#operation/getV2CryptocurrencyQuotesLatest">
     *     Documentation for get Exchange Rate API endpoint
     * </a>
     */
    @GetMapping("/v2/cryptocurrency/quotes/latest")
    ExchangeRateResponse getExchangeRate(
            @RequestHeader(AUTH_HEADER) String apiKey,
            @RequestParam(value = "slug") String fromCurrencyName,
            @RequestParam(value = "symbol") String fromCurrencyCode,
            @RequestParam(value = "convert") String toCurrencyCode);

}
package dev.sgd.currencymate.coinmarketcup.client;

import dev.sgd.currencymate.coinmarketcup.config.CoinmarketcupFeignClientConfig;
import dev.sgd.currencymate.coinmarketcup.config.CoinmarketcupFeignErrorDecoder;
import dev.sgd.currencymate.coinmarketcup.model.ExchangeRateResponse;
import dev.sgd.currencymate.coinmarketcup.model.currency.GetCurrenciesResponse;
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
     *     Documentation for Get Exchange Rate API endpoint
     * </a>
     */
    @GetMapping("/v2/cryptocurrency/quotes/latest")
    ExchangeRateResponse getExchangeRate(
            @RequestHeader(AUTH_HEADER) String apiKey,
            @RequestParam(value = "slug", required = false) String fromCurrencyName,      // bitcoin
            @RequestParam(value = "convert") String toCurrencyCode);                      // USD

    /**
     * <a href="https://coinmarketcap.com/api/documentation/v1/#operation/getV1CryptocurrencyMap">
     *     Documentation for Get Crypto Currencies API endpoint
     * </a>
     */
    @GetMapping("/v1/cryptocurrency/map")
    GetCurrenciesResponse getCryptoCurrencies(
            @RequestHeader(AUTH_HEADER) String apiKey);

    /**
     * <a href="https://coinmarketcap.com/api/documentation/v1/#operation/getV1FiatMap">
     *     Documentation for Get Fiat Currencies API endpoint
     * </a>
     */
    @GetMapping("/v1/fiat/map")
    GetCurrenciesResponse getFiatCurrencies(
            @RequestHeader(AUTH_HEADER) String apiKey,
            @RequestParam(value = "include_metals", defaultValue = "true", required = false) Boolean includeMetals);

}
package dev.sgd.currencymate.coinmarketcup.currency;

import dev.sgd.currencymate.coinmarketcup.client.CoinmarketcupClient;
import dev.sgd.currencymate.coinmarketcup.model.currency.CurrencyInfo;
import dev.sgd.currencymate.coinmarketcup.model.currency.GetCurrenciesResponse;
import dev.sgd.currencymate.domain.error.common.AdapterException;
import dev.sgd.currencymate.domain.error.common.ExternalServiceException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static dev.sgd.currencymate.coinmarketcup.mapper.CurrencyMapper.CURRENCY_MAPPER;
import static dev.sgd.currencymate.domain.enums.CurrencyType.CRYPTO;
import static dev.sgd.currencymate.domain.enums.CurrencyType.FIAT;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Component
public class CoinmarketcupCurrencyHandler {

    private static final String START_LOADING_CURRENCIES_LOG_MSG =
            """
            🪙 Loading Coinmarketcup {} and {} currencies from API...
            """;
    private static final String FINISHED_LOADING_CURRENCIES_LOG_MSG =
            """
            ☑️ Finished loading Coinmarketcup {} and {} currencies from API
            {} currencies count: {}
            {} currencies count: {}
            """;

    private static final String ERROR_LOADING_CURRENCIES_EMPTY_BODY_LOG_MSG =
            """
            ❌ Failed to load Coinmarketcup {} currencies from API, response body is empty
            """;
    private static final String ERROR_LOADING_CURRENCIES_LOG_MSG =
            """
            ❌ Failed to load Coinmarketcup {} currencies from API, error message: {}
            """;


    private final Map<String, CurrencyInfo> currencies;
    private final String apiKey;
    private final CoinmarketcupClient client;
    private final Logger logger;

    public CoinmarketcupCurrencyHandler(@Value("${app.adapter.exchangerate.apiKey}") String apiKey,
                                        CoinmarketcupClient client,
                                        @Qualifier("feignLogger") Logger logger) {
        this.currencies = new HashMap<>();
        this.apiKey = apiKey;
        this.client = client;
        this.logger = logger;
    }

    public Optional<CurrencyInfo> getCurrencyByCode(String currencyCode) {
        return Optional.ofNullable(currencies.get(currencyCode));
    }

    @Retryable(
            retryFor = { ExternalServiceException.class,
                    ConnectException.class,
                    SocketTimeoutException.class,
                    FeignException.InternalServerError.class },
            noRetryFor = { AdapterException.class },
            maxAttemptsExpression = "${app.adapter.exchangerate.retryMaxAttempts}",
            backoff = @Backoff(delayExpression = "${app.adapter.exchangerate.retryBackoffDelayMs}")
    )
    @EventListener(ApplicationReadyEvent.class)
    public void loadCryptoCurrenciesFromApi() {
        log.info(START_LOADING_CURRENCIES_LOG_MSG, CRYPTO, FIAT);

        List<CurrencyInfo> cryptoCurrencies = null;
        List<CurrencyInfo> fiatCurrencies = null;

        try {
            cryptoCurrencies = Optional.ofNullable(client.getCryptoCurrencies(apiKey))
                    .map(GetCurrenciesResponse::getData)
                    .map(CURRENCY_MAPPER::toDomainCrypto)
                    .filter(currencies -> !isEmpty(currencies))
                    .orElseThrow(() -> {
                        logger.error(ERROR_LOADING_CURRENCIES_EMPTY_BODY_LOG_MSG, CRYPTO);
                        return new AdapterException();
                    });

            Map<String, CurrencyInfo> cryptoCurrenciesMap = cryptoCurrencies.stream()
                    .collect(toMap(CurrencyInfo::getCode, identity()));
            currencies.putAll(cryptoCurrenciesMap);
        } catch (Exception e) {
            logger.error(ERROR_LOADING_CURRENCIES_LOG_MSG, CRYPTO, e.getMessage(), e);
            throw new AdapterException();
        }

        try {
            fiatCurrencies = Optional.ofNullable(client.getFiatCurrencies(apiKey, true))
                    .map(GetCurrenciesResponse::getData)
                    .map(CURRENCY_MAPPER::toDomainFiat)
                    .filter(currencies -> !isEmpty(currencies))
                    .orElseThrow(() -> {
                        logger.error(ERROR_LOADING_CURRENCIES_EMPTY_BODY_LOG_MSG, FIAT);
                        return new AdapterException();
                    });

            Map<String, CurrencyInfo> fiatCurrenciesMap = fiatCurrencies.stream()
                    .collect(toMap(CurrencyInfo::getCode, identity()));
            currencies.putAll(fiatCurrenciesMap);
        } catch (Exception e) {
            logger.error(ERROR_LOADING_CURRENCIES_LOG_MSG, FIAT, e.getMessage(), e);
            throw new AdapterException();
        }

        log.info(FINISHED_LOADING_CURRENCIES_LOG_MSG,
                CRYPTO, FIAT,
                CRYPTO, cryptoCurrencies.size(),
                FIAT, fiatCurrencies.size());
    }

}
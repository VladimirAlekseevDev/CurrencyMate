package dev.sgd.currencymate.exchangerate.currency;

import dev.sgd.currencymate.domain.error.common.AdapterException;
import dev.sgd.currencymate.domain.error.common.ExternalServiceException;
import dev.sgd.currencymate.domain.model.Currency;
import dev.sgd.currencymate.exchangerate.client.ExchangerateClient;
import dev.sgd.currencymate.exchangerate.model.AllCurrenciesResponse;
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

import static dev.sgd.currencymate.domain.enums.CurrencyType.FIAT;
import static dev.sgd.currencymate.exchangerate.ExchangerateAdapterImpl.API_KEY_PREFIX;
import static dev.sgd.currencymate.exchangerate.mapper.CurrencyMapper.CURRENCY_MAPPER;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Component
public class ExchangerateCurrencyHandler {

    private static final String START_LOADING_CURRENCIES_LOG_MSG =
            """
            🪙 Loading Exchangerate {} currencies from API...
            """;
    private static final String FINISHED_LOADING_CURRENCIES_LOG_MSG =
            """
            ☑️ Finished loading Exchangerate {} currencies from API, currencies count: {}
            """;

    private static final String ERROR_LOADING_CURRENCIES_EMPTY_BODY_LOG_MSG =
            """
            ❌ Failed to load Exchangerate {} currencies from API, response body is empty
            """;
    private static final String ERROR_LOADING_CURRENCIES_LOG_MSG =
            """
            ❌ Failed to load Exchangerate {} currencies from API, error message: {}
            """;


    private final Map<String, Currency> currencies;
    private final String apiKey;
    private final ExchangerateClient client;
    private final Logger logger;

    public ExchangerateCurrencyHandler(@Value("${app.adapter.exchangerate.apiKey}") String apiKey,
                                       ExchangerateClient client,
                                       @Qualifier("feignLogger") Logger logger) {
        this.currencies = new HashMap<>();
        this.apiKey = API_KEY_PREFIX + apiKey;
        this.client = client;
        this.logger = logger;
    }

    public Optional<Currency> getCurrencyByCode(String currencyCode) {
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
    public void loadCurrenciesFromApi() {
        log.info(START_LOADING_CURRENCIES_LOG_MSG, FIAT);

        try {
            List<Currency> fiatCurrencies = Optional.ofNullable(client.getAllCurrencies(apiKey))
                    .map(AllCurrenciesResponse::getSupportedCodes)
                    .map(CURRENCY_MAPPER::toDomains)
                    .filter(currencies -> !isEmpty(currencies))
                    .orElseThrow(() -> {
                        logger.error(ERROR_LOADING_CURRENCIES_EMPTY_BODY_LOG_MSG, FIAT);
                        return new AdapterException();
                    });


            Map<String, Currency> currencyMap = fiatCurrencies.stream()
                    .collect(toMap(Currency::getCode, identity()));
            currencies.putAll(currencyMap);
        } catch (Exception e) {
            logger.error(ERROR_LOADING_CURRENCIES_LOG_MSG, FIAT, e.getMessage(), e);
            throw new AdapterException();
        }

        log.info(FINISHED_LOADING_CURRENCIES_LOG_MSG, FIAT, currencies.size());
    }

}
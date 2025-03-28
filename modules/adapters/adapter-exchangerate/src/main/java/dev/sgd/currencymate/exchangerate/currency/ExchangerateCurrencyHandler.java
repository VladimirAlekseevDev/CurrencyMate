package dev.sgd.currencymate.exchangerate.currency;

import dev.sgd.currencymate.domain.error.common.AdapterException;
import dev.sgd.currencymate.domain.error.common.ExternalServiceException;
import dev.sgd.currencymate.domain.model.Currency;
import dev.sgd.currencymate.exchangerate.client.ExchangerateClient;
import dev.sgd.currencymate.exchangerate.model.AllCurrenciesResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.Optional;

import static dev.sgd.currencymate.domain.enums.CurrencyType.FIAT;
import static dev.sgd.currencymate.exchangerate.mapper.CurrencyMapper.CURRENCY_MAPPER;

@Slf4j
@Component
public class ExchangerateCurrencyHandler {

    private final String apiKey;
    private final ExchangerateClient client;
    private final Logger logger;

    private List<Currency> fiatCurrencies;

    public ExchangerateCurrencyHandler(@Value("${app.adapter.exchangerate.apiKey}") String apiKey,
                                       ExchangerateClient client,
                                       @Qualifier("feignLogger") Logger logger) {
        this.apiKey = apiKey;
        this.client = client;
        this.logger = logger;
    }

    public Optional<Currency> getCurrencyByCode(String currencyCode) {
        return fiatCurrencies.stream()
                .filter(currency -> currency.getCode().equals(currencyCode))
                .findFirst();
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
        log.info("Loading Exchangerate {} currencies from API", FIAT);

        fiatCurrencies = Optional.ofNullable(client.getAllCurrencies(apiKey))
                .map(AllCurrenciesResponse::getSupportedCodes)
                .map(CURRENCY_MAPPER::toDomain)
                .orElseThrow(() -> {
                    logger.error("Failed to load Exchangerate currencies from API");
                    return new AdapterException();
                });

        log.info("Loaded Exchangerate {} {} currencies from API", fiatCurrencies.size(), FIAT);
    }

}
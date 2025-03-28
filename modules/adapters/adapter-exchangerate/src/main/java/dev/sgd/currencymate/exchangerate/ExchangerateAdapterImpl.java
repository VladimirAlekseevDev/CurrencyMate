package dev.sgd.currencymate.exchangerate;

import dev.sgd.currencymate.domain.adapter.ExchangerateAdapter;
import dev.sgd.currencymate.domain.error.common.AdapterException;
import dev.sgd.currencymate.domain.error.common.ExternalServiceException;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.exchangerate.client.ExchangerateClient;
import dev.sgd.currencymate.exchangerate.model.ExchangeRateResponse;
import feign.FeignException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Optional;

import static dev.sgd.currencymate.exchangerate.mapper.ExchangeRateMapper.EXCHANGE_RATE_MAPPER;

@Component
public class ExchangerateAdapterImpl implements ExchangerateAdapter {

    private static final String SERVICE_NAME = "exchangerate";

    private final ExchangerateClient client;
    //private final AlphavantageCurrencyHandler currencyHandler;
    private final String apiKey;
    private final Logger logger;

    public ExchangerateAdapterImpl(ExchangerateClient client,
                                   //AlphavantageCurrencyHandler currencyHandler,
                                   @Value("${app.adapter.exchangerate.apiKey}") String apiKey,
                                   @Qualifier("feignLogger") Logger logger) {
        this.client = client;
        //this.currencyHandler = currencyHandler;
        this.apiKey = apiKey;
        this.logger = logger;
    }

    @Override
    @Retryable(
            retryFor = { ExternalServiceException.class,
                    ConnectException.class,
                    SocketTimeoutException.class,
                    FeignException.InternalServerError.class },
            noRetryFor = { AdapterException.class },
            maxAttemptsExpression = "${app.adapter.exchangerate.retryMaxAttempts}",
            backoff = @Backoff(delayExpression = "${app.adapter.exchangerate.retryBackoffDelayMs}")
    )
    public ExchangeRate getExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        logger.info("Getting exchange rate from service '{}', fromCurrencyCode: {}, toCurrencyCode: {}, retryCount: {}",
                SERVICE_NAME, fromCurrencyCode, toCurrencyCode, getRetryCount());

        ExchangeRateResponse response = client.getExchangeRate(apiKey, fromCurrencyCode, toCurrencyCode);

        // TODO: fill from.name and to.name
        return EXCHANGE_RATE_MAPPER.toDomain(response);
    }

    @Override
    public boolean canProvideCurrentExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        return false;
    }

    private Integer getRetryCount() {
        return Optional.ofNullable(RetrySynchronizationManager.getContext())
                .map(RetryContext::getRetryCount)
                .orElse(null);
    }

}
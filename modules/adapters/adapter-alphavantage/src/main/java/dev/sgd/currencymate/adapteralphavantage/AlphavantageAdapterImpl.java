package dev.sgd.currencymate.adapteralphavantage;

import dev.sgd.currencymate.adapteralphavantage.client.AlphavantageClient;
import dev.sgd.currencymate.adapteralphavantage.model.exchangerate.ExchangeRateResponse;
import dev.sgd.currencymate.adapteralphavantage.model.timeseries.DailyExchangeRateResponse;
import dev.sgd.currencymate.domain.adapter.AlphavantageAdapter;
import dev.sgd.currencymate.domain.error.common.AdapterException;
import dev.sgd.currencymate.domain.error.common.ExternalServiceException;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.ExchangeRateDaily;
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

import static dev.sgd.currencymate.adapteralphavantage.mapper.DailyExchangeRateResponseMapper.DAILY_EXCHANGE_RATE_RESPONSE_MAPPER;
import static dev.sgd.currencymate.adapteralphavantage.mapper.ExchangeRateMapper.EXCHANGE_RATE_MAPPER;

@Component
public class AlphavantageAdapterImpl implements AlphavantageAdapter {

    private static final String LOG_PREFIX = AlphavantageAdapterImpl.class.getSimpleName() + ":";

    private static final String SERVICE_NAME = "alphavantage";

    private static final String EXCHANGE_RATE_DAILY_OUTPUT_SIZE_COMPACT = "compact";
    private static final String EXCHANGE_RATE_DAILY_OUTPUT_SIZE_FULL = "full";

    private final AlphavantageClient client;
    private final String apiKey;
    private final Logger logger;

    public AlphavantageAdapterImpl(AlphavantageClient client,
                                   @Value("${app.adapter.alphavantage.apiKey}") String apiKey,
                                   @Qualifier("feignLogger") Logger logger) {
        this.client = client;
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
        maxAttemptsExpression = "${app.adapter.alphavantage.retryMaxAttempts}",
        backoff = @Backoff(delayExpression = "${app.adapter.alphavantage.retryBackoffDelayMs}")
    )
    public ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) {
        logger.info("{} Getting exchange rate from service '{}', fromCurrency: {}, toCurrency: {}, retryCount: {}",
            LOG_PREFIX, SERVICE_NAME, fromCurrency, toCurrency, getRetryCount());

        ExchangeRateResponse response = client.getExchangeRate("CURRENCY_EXCHANGE_RATE", fromCurrency, toCurrency, apiKey);

        return EXCHANGE_RATE_MAPPER.toDomain(response);
    }

    @Override
    @Retryable(
        retryFor = { ExternalServiceException.class,
                     ConnectException.class,
                     SocketTimeoutException.class,
                     FeignException.InternalServerError.class },
        noRetryFor = { AdapterException.class },
        maxAttemptsExpression = "${app.adapter.alphavantage.retryMaxAttempts}",
        backoff = @Backoff(delayExpression = "${app.adapter.alphavantage.retryBackoffDelayMs}")
    )
    public ExchangeRateDaily getExchangeRateDaily(String fromCurrency, String toCurrency) {
        logger.info("{} Getting exchange rate daily from service '{}', fromCurrency: {}, toCurrency: {}, retryCount: {}",
            LOG_PREFIX, SERVICE_NAME, fromCurrency, toCurrency, getRetryCount());

        DailyExchangeRateResponse response = client.getExchangeRateDaily(
                "FX_DAILY", fromCurrency, toCurrency, EXCHANGE_RATE_DAILY_OUTPUT_SIZE_COMPACT, apiKey);

        return DAILY_EXCHANGE_RATE_RESPONSE_MAPPER.toDomain(response);
    }

    private Integer getRetryCount() {
        return Optional.ofNullable(RetrySynchronizationManager.getContext())
                .map(RetryContext::getRetryCount)
                .orElse(null);
    }

}
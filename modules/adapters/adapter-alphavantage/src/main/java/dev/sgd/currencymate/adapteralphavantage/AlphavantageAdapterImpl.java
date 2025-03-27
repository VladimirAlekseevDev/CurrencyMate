package dev.sgd.currencymate.adapteralphavantage;

import dev.sgd.currencymate.adapteralphavantage.client.AlphavantageClient;
import dev.sgd.currencymate.adapteralphavantage.currency.AlphavantageCurrencyHandler;
import dev.sgd.currencymate.adapteralphavantage.model.exchangerate.ExchangeRateResponse;
import dev.sgd.currencymate.adapteralphavantage.model.timeseries.DailyExchangeRateResponse;
import dev.sgd.currencymate.domain.adapter.AlphavantageAdapter;
import dev.sgd.currencymate.domain.enums.CurrencyType;
import dev.sgd.currencymate.domain.error.common.AdapterException;
import dev.sgd.currencymate.domain.error.common.ExternalServiceException;
import dev.sgd.currencymate.domain.model.Currency;
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

import static dev.sgd.currencymate.adapteralphavantage.currency.CurrencyCodeEnum.*;
import static dev.sgd.currencymate.adapteralphavantage.mapper.DailyExchangeRateResponseMapper.DAILY_EXCHANGE_RATE_RESPONSE_MAPPER;
import static dev.sgd.currencymate.adapteralphavantage.mapper.ExchangeRateMapper.EXCHANGE_RATE_MAPPER;
import static dev.sgd.currencymate.domain.enums.CurrencyType.CRYPTO;
import static dev.sgd.currencymate.domain.enums.CurrencyType.FIAT;

@Component
public class AlphavantageAdapterImpl implements AlphavantageAdapter {

    private static final String SERVICE_NAME = "alphavantage";

    private static final String EXCHANGE_RATE_DAILY_OUTPUT_SIZE_COMPACT = "compact";
    private static final String EXCHANGE_RATE_DAILY_OUTPUT_SIZE_FULL = "full";

    private final AlphavantageClient client;
    private final AlphavantageCurrencyHandler currencyHandler;
    private final String apiKey;
    private final Logger logger;

    public AlphavantageAdapterImpl(AlphavantageClient client,
                                   AlphavantageCurrencyHandler currencyHandler,
                                   @Value("${app.adapter.alphavantage.apiKey}") String apiKey,
                                   @Qualifier("feignLogger") Logger logger) {
        this.client = client;
        this.currencyHandler = currencyHandler;
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
    public ExchangeRate getExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        logger.info("Getting exchange rate from service '{}', fromCurrencyCode: {}, toCurrencyCode: {}, retryCount: {}",
            SERVICE_NAME, fromCurrencyCode, toCurrencyCode, getRetryCount());

        ExchangeRateResponse response = client.getExchangeRate("CURRENCY_EXCHANGE_RATE", fromCurrencyCode, toCurrencyCode, apiKey);

        return EXCHANGE_RATE_MAPPER.toDomain(response);
    }

    @Override
    public boolean canProvideCurrentExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        Currency fromCurrency = currencyHandler.getCurrencyByCode(fromCurrencyCode).orElse(null);
        if (fromCurrency == null) {
            return false;
        }
        Currency toCurrency = currencyHandler.getCurrencyByCode(toCurrencyCode).orElse(null);
        if (toCurrency == null) {
            return false;
        }

        if (typesEquals(fromCurrency.getType(), FIAT, toCurrency.getType(), CRYPTO)
            || typesEquals(fromCurrency.getType(), CRYPTO, toCurrency.getType(), CRYPTO)) {
            return false;
        } else if (CRYPTO.equals(fromCurrency.getType())
            && FIAT.equals(toCurrency.getType())
            && !(USD.isEqualTo(toCurrency.getCode()) || EUR.isEqualTo(toCurrency.getCode()))) {
            return false;
        } else if (FIAT.equals(fromCurrency.getType())
            && FIAT.equals(toCurrency.getType())
            && !(USD.isEqualTo(fromCurrency.getCode()) || EUR.isEqualTo(fromCurrency.getCode()))) {
            return false;
        } else if (RUB.isEqualTo(fromCurrency.getCode()) || RUB.isEqualTo(toCurrency.getCode())) {
            return false;
        }

        return true;
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
    public ExchangeRateDaily getExchangeRateDaily(String fromCurrencyCode, String toCurrencyCode) {
        logger.info("Getting exchange rate daily from service '{}', fromCurrencyCode: {}, toCurrencyCode: {}, retryCount: {}",
            SERVICE_NAME, fromCurrencyCode, toCurrencyCode, getRetryCount());

        DailyExchangeRateResponse response = client.getExchangeRateDaily(
                "FX_DAILY", fromCurrencyCode, toCurrencyCode, EXCHANGE_RATE_DAILY_OUTPUT_SIZE_COMPACT, apiKey);

        Currency fromCurrency = currencyHandler.getCurrencyByCode(fromCurrencyCode).orElseThrow();
        Currency toCurrency = currencyHandler.getCurrencyByCode(toCurrencyCode).orElseThrow();

        ExchangeRateDaily exchangeRateDaily = DAILY_EXCHANGE_RATE_RESPONSE_MAPPER.toDomain(response);
        DAILY_EXCHANGE_RATE_RESPONSE_MAPPER.setCurrenciesNameAndType(exchangeRateDaily, fromCurrency, toCurrency);

        return exchangeRateDaily;
    }

    @Override
    public boolean canProvideDailyExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        Currency fromCurrency = currencyHandler.getCurrencyByCode(fromCurrencyCode).orElse(null);
        if (fromCurrency == null) {
            return false;
        }
        Currency toCurrency = currencyHandler.getCurrencyByCode(toCurrencyCode).orElse(null);
        if (toCurrency == null) {
            return false;
        }

        if (typesEquals(fromCurrency.getType(), FIAT, toCurrency.getType(), CRYPTO)
                || typesEquals(fromCurrency.getType(), CRYPTO, toCurrency.getType(), FIAT)
                || typesEquals(fromCurrency.getType(), CRYPTO, toCurrency.getType(), CRYPTO)) {
            return false;
        } else if (typesEquals(fromCurrency.getType(), FIAT, toCurrency.getType(), FIAT)
                && !(USD.isEqualTo(fromCurrency.getCode()) || EUR.isEqualTo(fromCurrency.getCode()))) {
            return false;
        }

        return true;
    }

    private boolean typesEquals(CurrencyType fromType, CurrencyType fromTypeExpected, CurrencyType toType, CurrencyType toTypeExpected) {
        return fromTypeExpected.equals(fromType)
                && toTypeExpected.equals(toType);
    }

    private Integer getRetryCount() {
        return Optional.ofNullable(RetrySynchronizationManager.getContext())
                .map(RetryContext::getRetryCount)
                .orElse(null);
    }

}
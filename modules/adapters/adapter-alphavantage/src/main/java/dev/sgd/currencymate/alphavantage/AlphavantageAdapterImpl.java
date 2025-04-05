package dev.sgd.currencymate.alphavantage;

import dev.sgd.currencymate.alphavantage.client.AlphavantageClient;
import dev.sgd.currencymate.alphavantage.currency.AlphavantageCurrencyHandler;
import dev.sgd.currencymate.alphavantage.model.daily.DailyExchangeRateResponse;
import dev.sgd.currencymate.alphavantage.model.exchangerate.ExchangeRateResponse;
import dev.sgd.currencymate.alphavantage.model.weekly.WeeklyExchangeRateResponse;
import dev.sgd.currencymate.domain.adapter.AlphavantageAdapter;
import dev.sgd.currencymate.domain.enums.CurrencyType;
import dev.sgd.currencymate.domain.error.common.AdapterException;
import dev.sgd.currencymate.domain.error.common.ExternalServiceException;
import dev.sgd.currencymate.domain.model.Currency;
import dev.sgd.currencymate.domain.model.DailyExchangeRate;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.WeeklyExchangeRate;
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

import static dev.sgd.currencymate.alphavantage.currency.CurrencyCodeEnum.*;
import static dev.sgd.currencymate.alphavantage.mapper.DailyExchangeRateResponseMapper.DAILY_EXCHANGE_RATE_RESPONSE_MAPPER;
import static dev.sgd.currencymate.alphavantage.mapper.ExchangeRateMapper.EXCHANGE_RATE_MAPPER;
import static dev.sgd.currencymate.alphavantage.mapper.WeeklyExchangeRateResponseMapper.WEEKLY_EXCHANGE_RATE_RESPONSE_MAPPER;
import static dev.sgd.currencymate.domain.enums.CurrencyType.CRYPTO;
import static dev.sgd.currencymate.domain.enums.CurrencyType.FIAT;

@Component
public class AlphavantageAdapterImpl implements AlphavantageAdapter {

    private static final String SERVICE_NAME = "alphavantage";

    private static final String EXCHANGE_RATE_DAILY_OUTPUT_SIZE_COMPACT = "compact";

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
        logger.info("Getting exchange rate from service '{}', fromCurrencyCode: {}, toCurrencyCode: {}, apiKey: {}, retryCount: {}",
            SERVICE_NAME, fromCurrencyCode, toCurrencyCode, apiKey.substring(0, 2), getRetryCount());

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
    public DailyExchangeRate getDailyExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        logger.info("Getting daily exchange rate from service '{}', fromCurrencyCode: {}, toCurrencyCode: {}, retryCount: {}",
            SERVICE_NAME, fromCurrencyCode, toCurrencyCode, getRetryCount());

        DailyExchangeRateResponse response = client.getDailyExchangeRate(
                "FX_DAILY", fromCurrencyCode, toCurrencyCode, EXCHANGE_RATE_DAILY_OUTPUT_SIZE_COMPACT, apiKey);

        Currency fromCurrency = currencyHandler.getCurrencyByCode(fromCurrencyCode).orElseThrow();
        Currency toCurrency = currencyHandler.getCurrencyByCode(toCurrencyCode).orElseThrow();

        DailyExchangeRate dailyExchangeRate = DAILY_EXCHANGE_RATE_RESPONSE_MAPPER.toDomain(response);
        DAILY_EXCHANGE_RATE_RESPONSE_MAPPER.setCurrenciesNameAndType(dailyExchangeRate, fromCurrency, toCurrency);

        return dailyExchangeRate;
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
    public WeeklyExchangeRate getWeeklyExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        logger.info("Getting weekly exchange rate from service '{}', fromCurrencyCode: {}, toCurrencyCode: {}, retryCount: {}",
                SERVICE_NAME, fromCurrencyCode, toCurrencyCode, getRetryCount());

        WeeklyExchangeRateResponse response = client.getWeeklyExchangeRate(
                "FX_WEEKLY", fromCurrencyCode, toCurrencyCode, apiKey);

        Currency fromCurrency = currencyHandler.getCurrencyByCode(fromCurrencyCode).orElseThrow();
        Currency toCurrency = currencyHandler.getCurrencyByCode(toCurrencyCode).orElseThrow();

        WeeklyExchangeRate weeklyExchangeRate = WEEKLY_EXCHANGE_RATE_RESPONSE_MAPPER.toDomain(response);
        WEEKLY_EXCHANGE_RATE_RESPONSE_MAPPER.setCurrenciesNameAndType(weeklyExchangeRate, fromCurrency, toCurrency);

        return weeklyExchangeRate;
    }

    @Override
    public boolean canProvideWeeklyExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        return canProvideDailyExchangeRate(fromCurrencyCode, toCurrencyCode);
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
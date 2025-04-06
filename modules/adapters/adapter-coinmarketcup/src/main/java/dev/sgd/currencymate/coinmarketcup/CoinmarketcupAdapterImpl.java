package dev.sgd.currencymate.coinmarketcup;

import dev.sgd.currencymate.domain.adapter.CoinmarketcupAdapter;
import dev.sgd.currencymate.domain.error.common.AdapterException;
import dev.sgd.currencymate.domain.error.common.ExternalServiceException;
import dev.sgd.currencymate.domain.model.Currency;
import dev.sgd.currencymate.domain.model.ExchangeRate;
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
public class CoinmarketcupAdapterImpl implements CoinmarketcupAdapter {

    private static final String SERVICE_NAME = "coinmarketcup";

    private final CoinmarketcupClient client;
    private final String apiKey;
    private final Logger logger;

    public CoinmarketcupAdapterImpl(CoinmarketcupClient client,
                                    @Value("${app.adapter.coinmarketcap.apiKey}") String apiKey,
                                    @Qualifier("feignLogger") Logger logger) {
        this.client = client;
        this.apiKey = API_KEY_PREFIX + apiKey;
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

        Currency fromCurrency = currencyHandler.getCurrencyByCode(fromCurrencyCode).orElseThrow();
        Currency toCurrency = currencyHandler.getCurrencyByCode(toCurrencyCode).orElseThrow();

        ExchangeRate exchangeRate = EXCHANGE_RATE_MAPPER.toDomain(response);
        EXCHANGE_RATE_MAPPER.setCurrenciesNameAndType(exchangeRate, fromCurrency, toCurrency);

        return exchangeRate;
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

        return true;
    }

    private Integer getRetryCount() {
        return Optional.ofNullable(RetrySynchronizationManager.getContext())
                .map(RetryContext::getRetryCount)
                .orElse(null);
    }

}
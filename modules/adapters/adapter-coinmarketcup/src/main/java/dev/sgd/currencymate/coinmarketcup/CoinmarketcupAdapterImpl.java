package dev.sgd.currencymate.coinmarketcup;

import dev.sgd.currencymate.coinmarketcup.client.CoinmarketcupClient;
import dev.sgd.currencymate.coinmarketcup.currency.CoinmarketcupCurrencyHandler;
import dev.sgd.currencymate.coinmarketcup.model.ExchangeRateDataDto;
import dev.sgd.currencymate.coinmarketcup.model.ExchangeRateResponse;
import dev.sgd.currencymate.coinmarketcup.model.ExchangeRateValueDto;
import dev.sgd.currencymate.coinmarketcup.model.currency.CurrencyInfo;
import dev.sgd.currencymate.domain.adapter.CoinmarketcupAdapter;
import dev.sgd.currencymate.domain.error.common.AdapterException;
import dev.sgd.currencymate.domain.error.common.ExternalServiceException;
import dev.sgd.currencymate.domain.model.ExchangeRate;
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

import static dev.sgd.currencymate.coinmarketcup.mapper.ExchangeRateMapper.EXCHANGE_RATE_MAPPER;
import static dev.sgd.currencymate.domain.enums.CurrencyType.CRYPTO;
import static dev.sgd.currencymate.domain.enums.CurrencyType.FIAT;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class CoinmarketcupAdapterImpl implements CoinmarketcupAdapter {

    private static final String SERVICE_NAME = "coinmarketcup";

    private final CoinmarketcupClient client;
    private final CoinmarketcupCurrencyHandler currencyHandler;
    private final String apiKey;
    private final Logger logger;

    public CoinmarketcupAdapterImpl(CoinmarketcupClient client,
                                    CoinmarketcupCurrencyHandler currencyHandler,
                                    @Value("${app.adapter.coinmarketcap.apiKey}") String apiKey,
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
            maxAttemptsExpression = "${app.adapter.exchangerate.retryMaxAttempts}",
            backoff = @Backoff(delayExpression = "${app.adapter.exchangerate.retryBackoffDelayMs}")
    )
    public ExchangeRate getExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        logger.info("Getting exchange rate from service '{}', fromCurrencyCode: {}, toCurrencyCode: {}, retryCount: {}",
                SERVICE_NAME, fromCurrencyCode, toCurrencyCode, getRetryCount());

        CurrencyInfo fromCurrency = currencyHandler.getCurrencyByCode(fromCurrencyCode).orElseThrow();
        CurrencyInfo toCurrency = currencyHandler.getCurrencyByCode(toCurrencyCode).orElseThrow();

        ExchangeRateResponse response = client.getExchangeRate(apiKey, fromCurrency.getSlug(), toCurrency.getCode());
        ExchangeRateDataDto exchangeRateData = Optional.ofNullable(response.getData())
                .filter(data -> !isEmpty(data))
                .map(data -> data.get("1"))
                .orElseThrow();
        ExchangeRateValueDto exchangeRateValue = Optional.ofNullable(exchangeRateData.getQuote())
                .filter(quotes -> !isEmpty(quotes))
                .map(quotes -> quotes.get(toCurrency.getCode()))
                .orElseThrow();

        ExchangeRate exchangeRate = EXCHANGE_RATE_MAPPER.toDomain(exchangeRateValue);
        EXCHANGE_RATE_MAPPER.setCurrenciesNameAndType(exchangeRate, fromCurrency, toCurrency);

        return exchangeRate;
    }

    @Override
    public boolean canProvideCurrentExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        CurrencyInfo fromCurrency = currencyHandler.getCurrencyByCode(fromCurrencyCode).orElse(null);
        if (fromCurrency == null || FIAT.equals(fromCurrency.getType())) {
            return false;
        }
        CurrencyInfo toCurrency = currencyHandler.getCurrencyByCode(toCurrencyCode).orElse(null);
        if (toCurrency == null || CRYPTO.equals(toCurrency.getType())) {
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
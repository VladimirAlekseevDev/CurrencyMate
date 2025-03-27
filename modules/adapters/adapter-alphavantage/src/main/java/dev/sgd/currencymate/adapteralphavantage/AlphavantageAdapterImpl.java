package dev.sgd.currencymate.adapteralphavantage;

import dev.sgd.currencymate.adapteralphavantage.client.AlphavantageClient;
import dev.sgd.currencymate.adapteralphavantage.model.exchangerate.ExchangeRateResponse;
import dev.sgd.currencymate.adapteralphavantage.model.timeseries.TimeSeriesDailyResponse;
import dev.sgd.currencymate.domain.adapter.AlphavantageAdapter;
import dev.sgd.currencymate.domain.error.common.AdapterException;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.ExchangeRateDaily;
import java.util.Optional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Component;

import static dev.sgd.currencymate.adapteralphavantage.mapper.ExchangeRateMapper.EXCHANGE_RATE_MAPPER;
import static dev.sgd.currencymate.adapteralphavantage.mapper.TimeSeriesMapper.TIME_SERIES_MAPPER;

@Component
public class AlphavantageAdapterImpl implements AlphavantageAdapter {

    private static final String SERVICE_NAME = "alphavantage";

    private static final String FUNC_CURR_EXCHANGE_RATE = "CURRENCY_EXCHANGE_RATE";

    private static final String FUNC_CURR_EXCHANGE_RATE_DAILY = "FX_DAILY";
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
    public ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) {
        try {
            logger.info("Getting exchange rate from service '{}', fromCurrency: {}, toCurrency: {}, retryCount: {}",
                SERVICE_NAME, fromCurrency, toCurrency,
                getRetryContext().map(RetryContext::getRetryCount).orElse(null));

            ExchangeRateResponse response = client.getExchangeRate(FUNC_CURR_EXCHANGE_RATE, fromCurrency, toCurrency, apiKey);

            return EXCHANGE_RATE_MAPPER.toDomain(response);
        } catch (Exception e) {
            logger.error("Error getting exchange rate from service '{}', fromCurrency: {}, toCurrency: {}, retryCount: {}, error: {}",
                SERVICE_NAME, fromCurrency, toCurrency,
                getRetryContext().map(RetryContext::getRetryCount).orElse(null),
                e.getMessage(),
                e);

            throw new AdapterException();
        }
    }

    @Override
    public ExchangeRateDaily getExchangeRateDaily(String fromCurrency, String toCurrency) {
        try {
            logger.info(
                "Getting exchange rate daily from service '{}', fromCurrency: {}, toCurrency: {}, retryCount: {}",
                SERVICE_NAME, fromCurrency, toCurrency,
                getRetryContext().map(RetryContext::getRetryCount).orElse(null));

            TimeSeriesDailyResponse response = client.getExchangeRateDaily(
                FUNC_CURR_EXCHANGE_RATE_DAILY, fromCurrency, toCurrency, EXCHANGE_RATE_DAILY_OUTPUT_SIZE_COMPACT, apiKey);

            return TIME_SERIES_MAPPER.toDomain(response);
        } catch (Exception e) {
            logger.error(
                "Error getting exchange rate daily from service '{}', fromCurrency: {}, toCurrency: {}, retryCount: {}, error: {}",
                SERVICE_NAME, fromCurrency, toCurrency,
                getRetryContext().map(RetryContext::getRetryCount).orElse(null),
                e.getMessage(),
                e);

            throw new AdapterException();
        }
    }

    private Optional<RetryContext> getRetryContext() {
        return Optional.ofNullable(RetrySynchronizationManager.getContext());
    }

}
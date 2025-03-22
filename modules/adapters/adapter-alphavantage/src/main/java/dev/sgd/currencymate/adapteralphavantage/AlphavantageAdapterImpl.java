package dev.sgd.currencymate.adapteralphavantage;

import dev.sgd.currencymate.adapteralphavantage.client.AlphavantageClient;
import dev.sgd.currencymate.adapteralphavantage.mapper.ExchangeRateMapper;
import dev.sgd.currencymate.adapteralphavantage.mapper.TimeSeriesMapper;
import dev.sgd.currencymate.adapteralphavantage.model.exchangerate.ExchangeRateResponse;
import dev.sgd.currencymate.adapteralphavantage.model.timeseries.TimeSeriesDailyResponse;
import dev.sgd.currencymate.domain.adapter.AlphavantageAdapter;
import dev.sgd.currencymate.domain.error.AdapterException;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.TimeSeries;
import java.util.Optional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Component;

@Component
public class AlphavantageAdapterImpl implements AlphavantageAdapter {

    private static final String SERVICE_NAME = "alphavantage";

    private static final String FUNC_CURR_EXCHANGE_RATE = "CURRENCY_EXCHANGE_RATE";

    private static final String FUNC_CURR_EXCHANGE_RATE_DAILY = "FX_DAILY";
    private static final String EXCHANGE_RATE_DAILY_OUTPUT_SIZE_COMPACT = "compact";
    private static final String EXCHANGE_RATE_DAILY_OUTPUT_SIZE_FULL = "full";

    private final AlphavantageClient client;
    private final Logger logger;
    private final String apiKey;

    public AlphavantageAdapterImpl(AlphavantageClient client,
                                   @Qualifier("feignLogger") Logger logger,
                                   @Value("${app.adapter.alphavantage.apiKey}") String apiKey) {
        this.client = client;
        this.logger = logger;
        this.apiKey = apiKey;
    }

    @Override
    public ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) {
        try {
            logger.info("Getting exchange rate from service '{}', fromCurrency: {}, toCurrency: {}, retryCount: {}",
                SERVICE_NAME, fromCurrency, toCurrency,
                getRetryContext().map(RetryContext::getRetryCount).orElse(null));

            ExchangeRateResponse response = client.getExchangeRate(FUNC_CURR_EXCHANGE_RATE, fromCurrency, toCurrency,
                apiKey);

            return ExchangeRateMapper.EXCHANGE_RATE_MAPPER.toDomain(response);
        } catch (Exception e) {
            logger.error("Error getting exchange rate from service '{}', fromCurrency: {}, toCurrency: {}, retryCount: {}, error: {}",
                SERVICE_NAME, fromCurrency, toCurrency,
                getRetryContext().map(RetryContext::getRetryCount).orElse(null),
                e.getMessage());

            throw new AdapterException();
        }
    }

    @Override
    public TimeSeries getExchangeRateDaily(String fromCurrency, String toCurrency) {
        try {
            logger.info(
                "Getting exchange rate daily from service '{}', fromCurrency: {}, toCurrency: {}, retryCount: {}",
                SERVICE_NAME, fromCurrency, toCurrency,
                getRetryContext().map(RetryContext::getRetryCount).orElse(null));

            TimeSeriesDailyResponse response = client.getExchangeRateDaily(
                FUNC_CURR_EXCHANGE_RATE_DAILY, fromCurrency, toCurrency, EXCHANGE_RATE_DAILY_OUTPUT_SIZE_COMPACT,
                apiKey);

            return TimeSeriesMapper.INSTANCE.toDomain(response);
        } catch (Exception e) {
            logger.error(
                "Error getting exchange rate daily from service '{}', fromCurrency: {}, toCurrency: {}, retryCount: {}, error: {}",
                SERVICE_NAME, fromCurrency, toCurrency,
                getRetryContext().map(RetryContext::getRetryCount).orElse(null),
                e.getMessage());

            throw new AdapterException();
        }
    }

    private Optional<RetryContext> getRetryContext() {
        return Optional.ofNullable(RetrySynchronizationManager.getContext());
    }

}
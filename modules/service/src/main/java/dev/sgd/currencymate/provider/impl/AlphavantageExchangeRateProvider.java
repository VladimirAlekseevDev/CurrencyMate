package dev.sgd.currencymate.provider.impl;

import dev.sgd.currencymate.domain.adapter.AlphavantageAdapter;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.TimeSeries;
import dev.sgd.currencymate.domain.utils.DateTimeUtils;
import dev.sgd.currencymate.provider.ExchangeRateProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static dev.sgd.currencymate.provider.ExchangeRateProviderEnum.ALPHAVANTAGE_PROVIDER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlphavantageExchangeRateProvider implements ExchangeRateProvider {

    private static final String LOG_PREFIX = ALPHAVANTAGE_PROVIDER.getName() + ":";

    private final AlphavantageAdapter alphavantageAdapter;

    @Override
    public ExchangeRate getCurrentExchangeRate(String fromCurrency, String toCurrency) {
        log.info("{} Getting exchange rate fromCurrency: {}, toCurrency: {}",
                LOG_PREFIX, fromCurrency, toCurrency);

        ExchangeRate exchangeRate = alphavantageAdapter.getExchangeRate(fromCurrency, toCurrency);
        exchangeRate.setReceivedAt(DateTimeUtils.getCurrentOffsetDateTime());

        log.info("{} Got exchange rate fromCurrency: {}, toCurrency: {}, exchangeRate: {}",
                LOG_PREFIX, fromCurrency, toCurrency, exchangeRate);

        return exchangeRate;
    }

    @Override
    public boolean canProvideCurrentExchangeRate(String fromCurrency, String toCurrency) {
        return true;
    }

    @Override
    public TimeSeries getDailyExchangeRate(String fromCurrency, String toCurrency) {
        log.info("Getting exchange rate daily fromCurrency: {}, toCurrency: {}", fromCurrency, toCurrency);

        TimeSeries timeSeries = alphavantageAdapter.getExchangeRateDaily(fromCurrency, toCurrency);
        timeSeries.setReceivedAt(DateTimeUtils.getCurrentOffsetDateTime());

        log.info("Got exchange rate daily fromCurrency: {}, toCurrency: {}, timeSeriesCount: {}",
                fromCurrency, toCurrency, timeSeries.getExchangeRateTimeSeries().size());

        return timeSeries;
    }

    @Override
    public boolean canProvideDailyExchangeRate(String fromCurrency, String toCurrency) {
        return true;
    }

    @Override
    public int getOrder() {
        return ALPHAVANTAGE_PROVIDER.getOrder();
    }

}

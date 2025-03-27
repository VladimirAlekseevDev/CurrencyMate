package dev.sgd.currencymate.provider.impl;

import dev.sgd.currencymate.domain.adapter.AlphavantageAdapter;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.ExchangeRateDaily;
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
        exchangeRate.setProviderName(ALPHAVANTAGE_PROVIDER.getName());

        log.info("{} Got exchange rate fromCurrency: {}, toCurrency: {}, exchangeRate: {}",
                LOG_PREFIX, fromCurrency, toCurrency, exchangeRate);

        return exchangeRate;
    }

    @Override
    public boolean canProvideCurrentExchangeRate(String fromCurrency, String toCurrency) {
        return true;
    }

    @Override
    public ExchangeRateDaily getDailyExchangeRate(String fromCurrency, String toCurrency) {
        log.info("{} Getting daily exchange rate fromCurrency: {}, toCurrency: {}",
                LOG_PREFIX, fromCurrency, toCurrency);

        ExchangeRateDaily exchangeRateDaily = alphavantageAdapter.getExchangeRateDaily(fromCurrency, toCurrency);
        exchangeRateDaily.setProviderName(ALPHAVANTAGE_PROVIDER.getName());

        log.info("{} Got daily exchange rate fromCurrency: {}, toCurrency: {}, timeSeriesCount: {}",
                LOG_PREFIX, fromCurrency, toCurrency, exchangeRateDaily.getExchangeRateTimeSeries().size());

        return exchangeRateDaily;
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

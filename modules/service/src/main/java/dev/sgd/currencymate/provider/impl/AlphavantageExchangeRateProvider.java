package dev.sgd.currencymate.provider.impl;

import dev.sgd.currencymate.domain.adapter.AlphavantageAdapter;
import dev.sgd.currencymate.domain.model.DailyExchangeRate;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.WeeklyExchangeRate;
import dev.sgd.currencymate.provider.ExchangeRateProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static dev.sgd.currencymate.provider.ExchangeRateProviderEnum.ALPHAVANTAGE_PROVIDER;

// TODO: turned off for now, waiting for payment
@Slf4j
//@Service
@RequiredArgsConstructor
public class AlphavantageExchangeRateProvider implements ExchangeRateProvider {


    private final AlphavantageAdapter alphavantageAdapter;

    @Override
    public ExchangeRate getCurrentExchangeRate(String fromCurrency, String toCurrency) {
        log.info("Getting exchange rate fromCurrency: {}, toCurrency: {}",
                fromCurrency, toCurrency);

        ExchangeRate exchangeRate = alphavantageAdapter.getExchangeRate(fromCurrency, toCurrency);
        exchangeRate.setProviderName(ALPHAVANTAGE_PROVIDER.getName());

        log.info("Got exchange rate fromCurrency: {}, toCurrency: {}, exchangeRate: {}",
                fromCurrency, toCurrency, exchangeRate);

        return exchangeRate;
    }

    @Override
    public boolean canProvideCurrentExchangeRate(String fromCurrency, String toCurrency) {
        return alphavantageAdapter.canProvideCurrentExchangeRate(fromCurrency, toCurrency);
    }

    @Override
    public DailyExchangeRate getDailyExchangeRate(String fromCurrency, String toCurrency) {
        log.info("Getting daily exchange rate fromCurrency: {}, toCurrency: {}",
                fromCurrency, toCurrency);

        DailyExchangeRate dailyExchangeRate = alphavantageAdapter.getDailyExchangeRate(fromCurrency, toCurrency);
        dailyExchangeRate.setProviderName(ALPHAVANTAGE_PROVIDER.getName());

        log.info("Got daily exchange rate fromCurrency: {}, toCurrency: {}, timeSeriesCount: {}",
                fromCurrency, toCurrency, dailyExchangeRate.getExchangeRateTimeSeries().size());

        return dailyExchangeRate;
    }

    @Override
    public boolean canProvideDailyExchangeRate(String fromCurrency, String toCurrency) {
        return alphavantageAdapter.canProvideDailyExchangeRate(fromCurrency, toCurrency);
    }

    @Override
    public WeeklyExchangeRate getWeeklyExchangeRate(String fromCurrency, String toCurrency) {
        log.info("Getting weekly exchange rate fromCurrency: {}, toCurrency: {}",
                fromCurrency, toCurrency);

        WeeklyExchangeRate weeklyExchangeRate = alphavantageAdapter.getWeeklyExchangeRate(fromCurrency, toCurrency);
        weeklyExchangeRate.setProviderName(ALPHAVANTAGE_PROVIDER.getName());

        log.info("Got weekly exchange rate fromCurrency: {}, toCurrency: {}, timeSeriesCount: {}",
                fromCurrency, toCurrency, weeklyExchangeRate.getExchangeRateTimeSeries().size());

        return weeklyExchangeRate;
    }

    @Override
    public boolean canProvideWeeklyExchangeRate(String fromCurrency, String toCurrency) {
        return alphavantageAdapter.canProvideWeeklyExchangeRate(fromCurrency, toCurrency);
    }


    @Override
    public int getOrder() {
        return ALPHAVANTAGE_PROVIDER.getOrder();
    }

}

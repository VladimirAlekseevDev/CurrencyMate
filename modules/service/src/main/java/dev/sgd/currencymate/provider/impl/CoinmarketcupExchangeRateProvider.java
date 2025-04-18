package dev.sgd.currencymate.provider.impl;

import dev.sgd.currencymate.domain.adapter.CoinmarketcupAdapter;
import dev.sgd.currencymate.domain.model.DailyExchangeRate;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.WeeklyExchangeRate;
import dev.sgd.currencymate.provider.ExchangeRateProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static dev.sgd.currencymate.provider.ExchangeRateProviderEnum.COINMARKETCUP_PROVIDER;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoinmarketcupExchangeRateProvider implements ExchangeRateProvider {

    private final CoinmarketcupAdapter coinmarketcupAdapter;

    @Override
    public ExchangeRate getCurrentExchangeRate(String fromCurrency, String toCurrency) {
        log.info("Getting exchange rate fromCurrency: {}, toCurrency: {}",
                fromCurrency, toCurrency);

        ExchangeRate exchangeRate = coinmarketcupAdapter.getExchangeRate(fromCurrency, toCurrency);
        exchangeRate.setProviderName(COINMARKETCUP_PROVIDER.getName());

        log.info("Got exchange rate fromCurrency: {}, toCurrency: {}, exchangeRate: {}",
                fromCurrency, toCurrency, exchangeRate);

        return exchangeRate;
    }

    @Override
    public boolean canProvideCurrentExchangeRate(String fromCurrency, String toCurrency) {
        return coinmarketcupAdapter.canProvideCurrentExchangeRate(fromCurrency, toCurrency);
    }

    @Override
    public DailyExchangeRate getDailyExchangeRate(String fromCurrency, String toCurrency) {
        return null;
    }

    @Override
    public boolean canProvideDailyExchangeRate(String fromCurrency, String toCurrency) {
        return false;
    }

    @Override
    public WeeklyExchangeRate getWeeklyExchangeRate(String fromCurrency, String toCurrency) {
        return null;
    }

    @Override
    public boolean canProvideWeeklyExchangeRate(String fromCurrency, String toCurrency) {
        return false;
    }

    @Override
    public int getOrder() {
        return COINMARKETCUP_PROVIDER.getOrder();
    }

}
package dev.sgd.currencymate.provider;

import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.TimeSeries;

/**
 * Interface for exchange rate data providers
 */
public interface ExchangeRateProvider {

    /**
     * Get current exchange rate from provider
     * @param fromCurrency currency to calculate exchange rate from
     * @param toCurrency currency to calculate exchange rate to
     * @return current exchange rate
     */
    ExchangeRate getExchangeRate(String fromCurrency, String toCurrency);

    /**
     * Get daily exchange rate from provider
     * @param fromCurrency currency to calculate exchange rate from
     * @param toCurrency currency to calculate exchange rate to
     * @return exchange rate daily
     */
    TimeSeries getExchangeRateDaily(String fromCurrency, String toCurrency);

}
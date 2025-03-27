package dev.sgd.currencymate.provider;

import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.TimeSeries;
import org.springframework.core.Ordered;

/**
 * Interface for exchange rate data providers
 */
public interface ExchangeRateProvider extends Ordered {

    /**
     * Get current exchange rate from provider
     * @param fromCurrency currency to get exchange rate from
     * @param toCurrency currency to get exchange rate to
     * @return current exchange rate
     */
    ExchangeRate getCurrentExchangeRate(String fromCurrency, String toCurrency);

    /**
     * Check if provider can provide exchange rate for given currencies
     * @param fromCurrency currency to get exchange rate from
     * @param toCurrency currency to get exchange rate to
     * @return true if provider can provide exchange rate via getExchangeRate(String,String) method,
     *         otherwise false
     */
    boolean canProvideCurrentExchangeRate(String fromCurrency, String toCurrency);

    /**
     * Get daily exchange rate from provider
     * @param fromCurrency currency to get exchange rate from
     * @param toCurrency currency to get exchange rate to
     * @return exchange rate daily
     */
    TimeSeries getDailyExchangeRate(String fromCurrency, String toCurrency);

    /**
     * Check if provider can provide daily exchange rate for given currencies
     * @param fromCurrency currency to get exchange rate from
     * @param toCurrency currency to get exchange rate to
     * @return true if provider can provide daily exchange rate via getDailyExchangeRate(String,String) method,
     *         otherwise false
     */
    boolean canProvideDailyExchangeRate(String fromCurrency, String toCurrency);

}
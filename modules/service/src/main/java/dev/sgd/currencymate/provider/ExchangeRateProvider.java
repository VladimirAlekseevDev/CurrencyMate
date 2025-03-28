package dev.sgd.currencymate.provider;

import dev.sgd.currencymate.domain.model.DailyExchangeRate;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.WeeklyExchangeRate;
import org.springframework.core.Ordered;

/**
 * Interface for exchange rate data providers
 */
public interface ExchangeRateProvider extends Ordered {

    /**
     * Get CURRENT exchange rate from provider
     * @param fromCurrency currency to get exchange rate from
     * @param toCurrency currency to get exchange rate to
     * @return CURRENT exchange rate
     */
    ExchangeRate getCurrentExchangeRate(String fromCurrency, String toCurrency);

    /**
     * Check if provider can provide CURRENT exchange rate for given currencies
     * @param fromCurrency currency to get exchange rate from
     * @param toCurrency currency to get exchange rate to
     * @return true if provider can provide exchange rate via getExchangeRate(String,String) method,
     *         otherwise false
     */
    boolean canProvideCurrentExchangeRate(String fromCurrency, String toCurrency);

    /**
     * Get DAILY exchange rate from provider
     * @param fromCurrency currency to get exchange rate from
     * @param toCurrency currency to get exchange rate to
     * @return exchange rate DAILY
     */
    DailyExchangeRate getDailyExchangeRate(String fromCurrency, String toCurrency);

    /**
     * Check if provider can provide DAILY exchange rate for given currencies
     * @param fromCurrency currency to get exchange rate from
     * @param toCurrency currency to get exchange rate to
     * @return true if provider can provide daily exchange rate via getDailyExchangeRate(String,String) method,
     *         otherwise false
     */
    boolean canProvideDailyExchangeRate(String fromCurrency, String toCurrency);

    /**
     * Get WEEKLY exchange rate from provider
     * @param fromCurrency currency to get exchange rate from
     * @param toCurrency currency to get exchange rate to
     * @return exchange rate WEEKLY
     */
    WeeklyExchangeRate getWeeklyExchangeRate(String fromCurrency, String toCurrency);

    /**
     * Check if provider can provide WEEKLY exchange rate for given currencies
     * @param fromCurrency currency to get exchange rate from
     * @param toCurrency currency to get exchange rate to
     * @return true if provider can provide weekly exchange rate via getWeeklyExchangeRate(String,String) method,
     *         otherwise false
     */
    boolean canProvideWeeklyExchangeRate(String fromCurrency, String toCurrency);

}
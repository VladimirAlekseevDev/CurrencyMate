package dev.sgd.currencymate.domain.adapter;

import dev.sgd.currencymate.domain.model.DailyExchangeRate;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.WeeklyExchangeRate;

public interface AlphavantageAdapter {

    ExchangeRate getExchangeRate(String fromCurrencyCode, String toCurrencyCode);

    /**
     * Works fine:
     * FIAT (USD/EUR) -> FIAT (USD/EUR/CNH/INR/JPY/TRY/THB/GEL/KZT/AMD/AZN)
     * CRYPTO (BTC/ETH) -> FIAT (USD/EUR)
     * Errors:
     * FIAT -> CRYPTO
     * CRYPTO -> CRYPTO
     * Special cases:
     * RUB currency code does not work in this methis
     */
    boolean canProvideCurrentExchangeRate(String fromCurrencyCode, String toCurrencyCode);

    DailyExchangeRate getDailyExchangeRate(String fromCurrencyCode, String toCurrencyCode);

    /**
     * Works fine:
     * FIAT (USD/EUR) -> FIAT (!RUB!/USD/EUR/CNH/INR/JPY/TRY/THB/GEL/KZT/AMD/AZN)
     * Errors:
     * CRYPTO -> CRYPTO
     * FIAT -> CRYPTO
     * CRYPTO -> FIAT
     */
    boolean canProvideDailyExchangeRate(String fromCurrencyCode, String toCurrencyCode);

    WeeklyExchangeRate getWeeklyExchangeRate(String fromCurrencyCode, String toCurrencyCode);

    boolean canProvideWeeklyExchangeRate(String fromCurrencyCode, String toCurrencyCode);

}
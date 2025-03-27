package dev.sgd.currencymate.domain.adapter;

import dev.sgd.currencymate.domain.error.common.AdapterException;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.ExchangeRateDaily;

public interface AlphavantageAdapter {

    ExchangeRate getExchangeRate(String fromCurrencyCode, String toCurrencyCode) throws AdapterException;

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

    ExchangeRateDaily getExchangeRateDaily(String fromCurrencyCode, String toCurrencyCode) throws AdapterException;

    /**
     * Works fine:
     * FIAT (USD/EUR) -> FIAT (!RUB!/USD/EUR/CNH/INR/JPY/TRY/THB/GEL/KZT/AMD/AZN)
     * Errors:
     * CRYPTO -> CRYPTO
     * FIAT -> CRYPTO
     * CRYPTO -> FIAT
     */
    boolean canProvideDailyExchangeRate(String fromCurrencyCode, String toCurrencyCode);

}
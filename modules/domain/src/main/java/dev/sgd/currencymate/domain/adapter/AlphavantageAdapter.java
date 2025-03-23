package dev.sgd.currencymate.domain.adapter;

import dev.sgd.currencymate.domain.error.AdapterException;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.TimeSeries;

public interface AlphavantageAdapter {

    /**
     * Works fine:
     * FIAT (USD/EUR) -> FIAT (EUR/CNH/INR/JPY/TRY/THB/GEL/KZT/AMD/AZN)
     * CRYPTO (BTC/ETH) -> FIAT (USD/EUR)
     * Errors: TODO - handle
     * USD -> RUB
     * TON -> FIAT (because no TON in crypto currencies)
     * CRYPTO -> RUB/GEL
     * C
     */
    ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) throws AdapterException;

    TimeSeries getExchangeRateDaily(String fromCurrency, String toCurrency) throws AdapterException;

}
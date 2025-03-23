package dev.sgd.currencymate.domain.adapter;

import dev.sgd.currencymate.domain.error.AdapterException;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.TimeSeries;

public interface AlphavantageAdapter {

    /**
     * Works fine:
     * USD/EUR -> EUR/CNH/TRY/THB/GEL/AMD/AZN
     * CRYPTO -> USD/EUR
     * Erros: TODO - handle
     * USD -> RUB
     * CRYPTO -> RUB/GEL
     * C
     */
    ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) throws AdapterException;

    TimeSeries getExchangeRateDaily(String fromCurrency, String toCurrency) throws AdapterException;

}
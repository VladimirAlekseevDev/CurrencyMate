package dev.sgd.currencymate.domain.adapter;

import dev.sgd.currencymate.domain.error.AdapterException;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.TimeSeries;

public interface ExchangeRateAdapter {

    ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) throws AdapterException;

    TimeSeries getExchangeRateDaily(String fromCurrency, String toCurrency) throws AdapterException;

}
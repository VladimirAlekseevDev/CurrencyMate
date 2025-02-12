package dev.sgd.currencymate.domain.adapter;

import dev.sgd.currencymate.domain.error.AdapterException;
import dev.sgd.currencymate.domain.model.ExchangeRate;

public interface ExchangeRateAdapter {

    ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) throws AdapterException;

}
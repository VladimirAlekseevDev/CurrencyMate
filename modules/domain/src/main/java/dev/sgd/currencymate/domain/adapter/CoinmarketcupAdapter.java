package dev.sgd.currencymate.domain.adapter;

import dev.sgd.currencymate.domain.model.ExchangeRate;

public interface CoinmarketcupAdapter {

    ExchangeRate getExchangeRate(String fromCurrencyCode, String toCurrencyCode);

    boolean canProvideCurrentExchangeRate(String fromCurrencyCode, String toCurrencyCode);

}
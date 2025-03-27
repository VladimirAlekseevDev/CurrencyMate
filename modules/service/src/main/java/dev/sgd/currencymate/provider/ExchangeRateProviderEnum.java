package dev.sgd.currencymate.provider;

import dev.sgd.currencymate.provider.impl.AlphavantageExchangeRateProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExchangeRateProviderEnum {

    ALPHAVANTAGE_PROVIDER(1, AlphavantageExchangeRateProvider.class.getSimpleName());

    private final int order;
    private final String name;

}
package dev.sgd.currencymate.provider;

import dev.sgd.currencymate.provider.impl.AlphavantageExchangeRateProvider;
import dev.sgd.currencymate.provider.impl.CoinmarketcupExchangeRateProvider;
import dev.sgd.currencymate.provider.impl.ExchangerateExchangeRateProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExchangeRateProviderEnum {

    EXCHANGERATE_PROVIDER(1, ExchangerateExchangeRateProvider.class.getSimpleName()),
    COINMARKETCUP_PROVIDER(2, CoinmarketcupExchangeRateProvider.class.getSimpleName()),
    ALPHAVANTAGE_PROVIDER(3, AlphavantageExchangeRateProvider.class.getSimpleName()),
    ;

    private final int order;
    private final String name;

}
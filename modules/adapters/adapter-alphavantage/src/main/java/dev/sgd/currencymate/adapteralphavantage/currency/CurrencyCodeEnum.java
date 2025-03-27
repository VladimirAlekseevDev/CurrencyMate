package dev.sgd.currencymate.adapteralphavantage.currency;


public enum CurrencyCodeEnum {

    /* Fiat */
    RUB,
    USD,
    EUR,
    CNH,
    INR,
    JPY,
    TRY,
    THB,
    GEL,
    KZT,
    AMD,
    AZN,

    /* Crypto */
    BTC,
    ETH,
    SOL;

    public boolean isEqualTo(String code) {
        return this.name().equals(code);
    }
}
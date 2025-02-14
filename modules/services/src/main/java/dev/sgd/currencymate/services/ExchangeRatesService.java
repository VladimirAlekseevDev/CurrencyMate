package dev.sgd.currencymate.services;

import dev.sgd.currencymate.domain.adapter.ExchangeRateAdapter;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRatesService {

    private final ExchangeRateAdapter exchangeRateAdapter;

    public ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) {
        // TODO добавить логирование
        ExchangeRate exchangeRate = exchangeRateAdapter.getExchangeRate(fromCurrency, toCurrency);

        exchangeRate.setReceivedAt(DateTimeUtils.getCurrentOffsetDateTime());

        return exchangeRate;
    }

}
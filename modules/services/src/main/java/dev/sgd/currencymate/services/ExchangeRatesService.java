package dev.sgd.currencymate.services;

import dev.sgd.currencymate.domain.adapter.AlphavantageAdapter;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.TimeSeries;
import dev.sgd.currencymate.domain.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRatesService {

    private final AlphavantageAdapter alphavantageAdapter;

    public ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) {
        // TODO добавить логирование
        ExchangeRate exchangeRate = alphavantageAdapter.getExchangeRate(fromCurrency, toCurrency);

        exchangeRate.setReceivedAt(DateTimeUtils.getCurrentOffsetDateTime());

        return exchangeRate;
    }

    public TimeSeries getExchangeRateDaily(String fromCurrency, String toCurrency) {
        TimeSeries timeSeries = alphavantageAdapter.getExchangeRateDaily(fromCurrency, toCurrency);

        timeSeries.setReceivedAt(DateTimeUtils.getCurrentOffsetDateTime());

        return timeSeries;
    }

}
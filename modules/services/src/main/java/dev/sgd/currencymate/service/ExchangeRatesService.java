package dev.sgd.currencymate.service;

import dev.sgd.currencymate.domain.adapter.AlphavantageAdapter;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.model.TimeSeries;
import dev.sgd.currencymate.domain.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRatesService {

    private final AlphavantageAdapter alphavantageAdapter;

    public ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) {
        log.info("Getting exchange rate fromCurrency: {}, toCurrency: {}", fromCurrency, toCurrency);

        ExchangeRate exchangeRate = alphavantageAdapter.getExchangeRate(fromCurrency, toCurrency);
        exchangeRate.setReceivedAt(DateTimeUtils.getCurrentOffsetDateTime());

        log.info("Got exchange rate fromCurrency: {}, toCurrency: {}, exchangeRate: {}",
            fromCurrency, toCurrency, exchangeRate);

        return exchangeRate;
    }

    public TimeSeries getExchangeRateDaily(String fromCurrency, String toCurrency) {
        log.info("Getting exchange rate daily fromCurrency: {}, toCurrency: {}", fromCurrency, toCurrency);

        TimeSeries timeSeries = alphavantageAdapter.getExchangeRateDaily(fromCurrency, toCurrency);
        timeSeries.setReceivedAt(DateTimeUtils.getCurrentOffsetDateTime());

        log.info("Got exchange rate daily fromCurrency: {}, toCurrency: {}, timeSeriesCount: {}",
            fromCurrency, toCurrency, timeSeries.getExchangeRateTimeSeries().size());

        return timeSeries;
    }

}
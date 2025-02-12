package dev.sgd.currencymate.services;

import static java.time.temporal.ChronoUnit.SECONDS;

import dev.sgd.currencymate.domain.adapter.ExchangeRateAdapter;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRatesService {

    private final ExchangeRateAdapter exchangeRateAdapter;

    public ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) {
        // TODO добавить логирование
        ExchangeRate exchangeRate = exchangeRateAdapter.getExchangeRate(fromCurrency, toCurrency);

        exchangeRate.setReceivedAt(OffsetDateTime.now().truncatedTo(SECONDS));

        return exchangeRate;
    }

}
package dev.sgd.currencymate.usecase;

import dev.sgd.currencymate.domain.error.specific.FindExchangeRateProviderException;
import dev.sgd.currencymate.domain.model.WeeklyExchangeRate;
import dev.sgd.currencymate.provider.ExchangeRateProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class GetWeeklyExchangeRatesUseCase {

    private final List<ExchangeRateProvider> exchangeRateProviders;

    public GetWeeklyExchangeRatesUseCase(List<ExchangeRateProvider> exchangeRateProviders) {
        this.exchangeRateProviders = exchangeRateProviders;
    }

    public WeeklyExchangeRate getWeeklyExchangeRate(String fromCurrency, String toCurrency) {
        log.info("Getting weekly exchange rate fromCurrency: {}, toCurrency: {} using exchange rate providers",
                fromCurrency, toCurrency);

        List<ExchangeRateProvider> suitableProviders = getSuitableProviders(fromCurrency, toCurrency);

        WeeklyExchangeRate weeklyExchangeRate = getWeeklyExchangeRate(suitableProviders, fromCurrency, toCurrency);

        log.info("Got weekly exchange rate fromCurrency: {}, toCurrency: {}, provider: {}, timeSeriesCount: {}",
                fromCurrency, toCurrency, weeklyExchangeRate.getProviderName(), weeklyExchangeRate.getExchangeRateTimeSeries().size());

        return weeklyExchangeRate;
    }

    private List<ExchangeRateProvider> getSuitableProviders(String fromCurrency, String toCurrency) {
        List<ExchangeRateProvider> suitableProviders = exchangeRateProviders.stream()
                .filter(provider -> provider.canProvideWeeklyExchangeRate(fromCurrency, toCurrency))
                .toList();

        if (suitableProviders.isEmpty()) {
            log.error("No exchange rate provider found for getWeeklyExchangeRate method, fromCurrency: {}, toCurrency: {}",
                    fromCurrency, toCurrency);

            throw new FindExchangeRateProviderException();
        }

        return suitableProviders;
    }

    private WeeklyExchangeRate getWeeklyExchangeRate(List<ExchangeRateProvider> suitableProviders, String fromCurrency, String toCurrency) {
        WeeklyExchangeRate weeklyExchangeRate = null;
        RuntimeException lastException = null;
        Iterator<ExchangeRateProvider> providerIterator = suitableProviders.iterator();

        do {
            try {
                weeklyExchangeRate = providerIterator.next().getWeeklyExchangeRate(fromCurrency, toCurrency);
            } catch (RuntimeException e) {
                lastException = e;
            }
        } while (weeklyExchangeRate == null && providerIterator.hasNext());

        if (weeklyExchangeRate == null) {
            List<String> suitableProvidersNames = suitableProviders.stream()
                    .map(p -> p.getClass().getSimpleName())
                    .toList();

            if (lastException != null) {
                log.error("Error getting weekly exchange rate from providers {}, last exception: {}",
                        suitableProvidersNames, lastException.getMessage());

                throw lastException;
            } else {
                log.error("Error getting weekly exchange rate from providers {}, no exception received",
                        suitableProvidersNames);

                throw new FindExchangeRateProviderException();
            }
        }

        return weeklyExchangeRate;
    }

}
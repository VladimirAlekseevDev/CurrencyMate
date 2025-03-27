package dev.sgd.currencymate.usecase;

import dev.sgd.currencymate.domain.error.specific.FindExchangeRateProviderException;
import dev.sgd.currencymate.domain.model.DailyExchangeRate;
import dev.sgd.currencymate.provider.ExchangeRateProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class GetDailyExchangeRatesUseCase {

    private final List<ExchangeRateProvider> exchangeRateProviders;

    public GetDailyExchangeRatesUseCase(List<ExchangeRateProvider> exchangeRateProviders) {
        this.exchangeRateProviders = exchangeRateProviders;
    }

    public DailyExchangeRate getDailyExchangeRate(String fromCurrency, String toCurrency) {
        log.info("Getting daily exchange rate fromCurrency: {}, toCurrency: {} using exchange rate providers",
                fromCurrency, toCurrency);

        List<ExchangeRateProvider> suitableProviders = getSuitableProviders(fromCurrency, toCurrency);

        DailyExchangeRate dailyExchangeRate = getExchangeRateDaily(suitableProviders, fromCurrency, toCurrency);

        log.info("Got daily exchange rate fromCurrency: {}, toCurrency: {}, provider: {}, timeSeriesCount: {}",
                fromCurrency, toCurrency, dailyExchangeRate.getProviderName(), dailyExchangeRate.getExchangeRateTimeSeries().size());

        return dailyExchangeRate;
    }

    private List<ExchangeRateProvider> getSuitableProviders(String fromCurrency, String toCurrency) {
        List<ExchangeRateProvider> suitableProviders = exchangeRateProviders.stream()
                .filter(provider -> provider.canProvideDailyExchangeRate(fromCurrency, toCurrency))
                .toList();

        if (suitableProviders.isEmpty()) {
            log.error("No exchange rate provider found for getDailyExchangeRate method, fromCurrency: {}, toCurrency: {}",
                    fromCurrency, toCurrency);

            throw new FindExchangeRateProviderException();
        }

        return suitableProviders;
    }

    private DailyExchangeRate getExchangeRateDaily(List<ExchangeRateProvider> suitableProviders, String fromCurrency, String toCurrency) {
        DailyExchangeRate dailyExchangeRate = null;
        RuntimeException lastException = null;
        Iterator<ExchangeRateProvider> providerIterator = suitableProviders.iterator();

        do {
            try {
                dailyExchangeRate = providerIterator.next().getDailyExchangeRate(fromCurrency, toCurrency);
            } catch (RuntimeException e) {
                lastException = e;
            }
        } while (dailyExchangeRate == null && providerIterator.hasNext());

        if (dailyExchangeRate == null) {
            List<String> suitableProvidersNames = suitableProviders.stream()
                    .map(p -> p.getClass().getSimpleName())
                    .toList();

            if (lastException != null) {
                log.error("Error getting daily exchange rate from providers {}, last exception: {}",
                        suitableProvidersNames, lastException.getMessage());

                throw lastException;
            } else {
                log.error("Error getting daily exchange rate from providers {}, no exception received",
                        suitableProvidersNames);

                throw new FindExchangeRateProviderException();
            }
        }

        return dailyExchangeRate;
    }

}
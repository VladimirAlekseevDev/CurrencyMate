package dev.sgd.currencymate.usecase;

import dev.sgd.currencymate.domain.error.specific.FindExchangeRateProviderException;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.provider.ExchangeRateProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetCurrentExchangeRateUseCase {

    private final List<ExchangeRateProvider> exchangeRateProviders;

    public ExchangeRate getCurrentExchangeRate(String fromCurrency, String toCurrency) {
        log.info("Getting current exchange rate fromCurrency: {}, toCurrency: {} using exchange rate providers",
                fromCurrency, toCurrency);

        List<ExchangeRateProvider> suitableProviders = getSuitableProviders(fromCurrency, toCurrency);

        ExchangeRate exchangeRate = getExchangeRate(suitableProviders, fromCurrency, toCurrency);

        log.info("Got current exchange rate fromCurrency: {}, toCurrency: {}, provider: {}, exchangeRate: {}",
                fromCurrency, toCurrency, exchangeRate.getProviderName(), exchangeRate);

        return exchangeRate;
    }

    private List<ExchangeRateProvider> getSuitableProviders(String fromCurrency, String toCurrency) {
        List<ExchangeRateProvider> suitableProviders = exchangeRateProviders.stream()
                .filter(provider -> provider.canProvideCurrentExchangeRate(fromCurrency, toCurrency))
                .toList();

        if (suitableProviders.isEmpty()) {
            log.error("No exchange rate provider found for getCurrentExchangeRate method, fromCurrency: {}, toCurrency: {}",
                    fromCurrency, toCurrency);

            throw new FindExchangeRateProviderException();
        }

        return suitableProviders;
    }

    private ExchangeRate getExchangeRate(List<ExchangeRateProvider> suitableProviders, String fromCurrency, String toCurrency) {
        ExchangeRate exchangeRate = null;
        RuntimeException lastException = null;
        Iterator<ExchangeRateProvider> providerIterator = suitableProviders.iterator();

        do {
            try {
                exchangeRate = providerIterator.next().getCurrentExchangeRate(fromCurrency, toCurrency);
            } catch (RuntimeException e) {
                lastException = e;
            }
        } while (exchangeRate == null && providerIterator.hasNext());

        if (exchangeRate == null) {
            List<String> suitableProvidersNames = suitableProviders.stream()
                    .map(p -> p.getClass().getSimpleName())
                    .toList();

            if (lastException != null) {
                log.error("Error getting exchange rate from providers {}, last exception: {}",
                        suitableProvidersNames, lastException.getMessage());

                throw lastException;
            } else {
                log.error("Error getting exchange rate from providers {}, no exception received",
                        suitableProvidersNames);

                throw new FindExchangeRateProviderException();
            }
        }

        return exchangeRate;
    }

}
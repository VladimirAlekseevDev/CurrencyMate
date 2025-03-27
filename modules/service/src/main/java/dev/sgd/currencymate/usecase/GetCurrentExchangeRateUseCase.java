package dev.sgd.currencymate.usecase;

import dev.sgd.currencymate.domain.error.specific.FindExchangeRateProviderException;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.provider.ExchangeRateProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class GetCurrentExchangeRateUseCase {

    private static final String LOG_PREFIX = GetCurrentExchangeRateUseCase.class.getSimpleName() + ":";

    private final List<ExchangeRateProvider> exchangeRateProviders;

    public GetCurrentExchangeRateUseCase(List<ExchangeRateProvider> exchangeRateProviders) {
        this.exchangeRateProviders = exchangeRateProviders;
    }

    public ExchangeRate getCurrentExchangeRate(String fromCurrency, String toCurrency) {
        log.info("{} Getting current exchange rate fromCurrency: {}, toCurrency: {} using exchange rate providers",
                LOG_PREFIX, fromCurrency, toCurrency);

        List<ExchangeRateProvider> suitableProviders = exchangeRateProviders.stream()
                .filter(provider -> provider.canProvideCurrentExchangeRate(fromCurrency, toCurrency))
                .toList();
        if (suitableProviders.isEmpty()) {
            log.error("{} No exchange rate provider found for getCurrentExchangeRate method, fromCurrency: {}, toCurrency: {}",
                    LOG_PREFIX, fromCurrency, toCurrency);

            throw new FindExchangeRateProviderException();
        }

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

        if (exchangeRate == null && lastException != null) {
            log.error("{} Error getting exchange rate from providers {}, last exception: {}",
                LOG_PREFIX, suitableProviders.stream().map(p -> p.getClass().getSimpleName()).toList(), lastException.getMessage());

            throw lastException;
        }

        log.info("{} Got current exchange rate fromCurrency: {}, toCurrency: {}, provider: {}, exchangeRate: {}",
                LOG_PREFIX, fromCurrency, toCurrency, exchangeRate.getProviderName(), exchangeRate);

        return exchangeRate;
    }

}
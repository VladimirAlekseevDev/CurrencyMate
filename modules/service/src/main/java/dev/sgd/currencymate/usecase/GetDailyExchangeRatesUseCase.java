package dev.sgd.currencymate.usecase;

import dev.sgd.currencymate.domain.error.specific.FindExchangeRateProviderException;
import dev.sgd.currencymate.domain.model.ExchangeRateDaily;
import dev.sgd.currencymate.provider.ExchangeRateProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class GetDailyExchangeRatesUseCase {

    private static final String LOG_PREFIX = GetDailyExchangeRatesUseCase.class.getSimpleName() + ":";

    private final List<ExchangeRateProvider> exchangeRateProviders;

    public GetDailyExchangeRatesUseCase(List<ExchangeRateProvider> exchangeRateProviders) {
        this.exchangeRateProviders = exchangeRateProviders;
    }

    public ExchangeRateDaily getDailyExchangeRate(String fromCurrency, String toCurrency) {
        log.info("{} Getting daily exchange rate fromCurrency: {}, toCurrency: {} using exchange rate providers",
                LOG_PREFIX, fromCurrency, toCurrency);

        List<ExchangeRateProvider> suitableProviders = exchangeRateProviders.stream()
                .filter(provider -> provider.canProvideDailyExchangeRate(fromCurrency, toCurrency))
                .toList();
        if (suitableProviders.isEmpty()) {
            log.error("{} No exchange rate provider found for getDailyExchangeRate method, fromCurrency: {}, toCurrency: {}",
                    LOG_PREFIX, fromCurrency, toCurrency);

            throw new FindExchangeRateProviderException();
        }

        ExchangeRateDaily exchangeRateDaily = null;
        RuntimeException lastException = null;
        Iterator<ExchangeRateProvider> providerIterator = suitableProviders.iterator();

        do {
            try {
                exchangeRateDaily = providerIterator.next().getDailyExchangeRate(fromCurrency, toCurrency);
            } catch (RuntimeException e) {
                lastException = e;
            }
        } while (exchangeRateDaily == null && providerIterator.hasNext());

        if (exchangeRateDaily == null && lastException != null) {
            log.error("{} Error getting daily exchange rate from providers {}, last exception: {}",
                    LOG_PREFIX, suitableProviders.stream().map(p -> p.getClass().getSimpleName()).toList(), lastException.getMessage());

            throw lastException;
        }

        log.info("{} Got daily exchange rate fromCurrency: {}, toCurrency: {}, provider: {}, timeSeries: {}",
                LOG_PREFIX, fromCurrency, toCurrency, exchangeRateDaily.getProviderName(), exchangeRateDaily);

        return exchangeRateDaily;
    }

}
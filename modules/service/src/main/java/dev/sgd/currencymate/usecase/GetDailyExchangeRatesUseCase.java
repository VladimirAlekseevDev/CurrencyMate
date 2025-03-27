package dev.sgd.currencymate.usecase;

import dev.sgd.currencymate.domain.error.specific.FindExchangeRateProviderException;
import dev.sgd.currencymate.domain.model.ExchangeRateDaily;
import dev.sgd.currencymate.provider.ExchangeRateProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

        ExchangeRateDaily exchangeRateDaily = exchangeRateProviders.stream()
                .filter(provider -> provider.canProvideDailyExchangeRate(fromCurrency, toCurrency))
                .findFirst()
                .map(provider -> provider.getDailyExchangeRate(fromCurrency, toCurrency))
                .orElseThrow(() -> {
                    log.warn("{} No exchange rate provider found for getDailyExchangeRate method, fromCurrency: {}, toCurrency: {}",
                            LOG_PREFIX, fromCurrency, toCurrency);

                    return new FindExchangeRateProviderException();
                });

        log.info("{} Got daily exchange rate fromCurrency: {}, toCurrency: {}, provider: {}, timeSeries: {}",
                LOG_PREFIX, fromCurrency, toCurrency, exchangeRateDaily.getProviderName(), exchangeRateDaily);

        return exchangeRateDaily;
    }

}
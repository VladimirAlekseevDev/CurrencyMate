package dev.sgd.currencymate.usecase;

import dev.sgd.currencymate.domain.error.specific.FindExchangeRateProviderException;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.provider.ExchangeRateProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GetCurrentExchangeRateUseCase {

    private final List<ExchangeRateProvider> exchangeRateProviders;

    public GetCurrentExchangeRateUseCase(List<ExchangeRateProvider> exchangeRateProviders) {
        this.exchangeRateProviders = exchangeRateProviders;
    }

    public ExchangeRate getCurrentExchangeRate(String fromCurrency, String toCurrency) {
        log.info("Getting current exchange rate fromCurrency: {}, toCurrency: {} using exchange rate providers",
                fromCurrency, toCurrency);

        ExchangeRate exchangeRate = exchangeRateProviders.stream()
                .filter(provider -> provider.canProvideCurrentExchangeRate(fromCurrency, toCurrency))
                .findFirst()
                .map(provider -> provider.getCurrentExchangeRate(fromCurrency, toCurrency))
                .orElseThrow(() -> {
                    log.warn("No exchange rate provider found for getCurrentExchangeRate method, fromCurrency: {}, toCurrency: {}", fromCurrency, toCurrency);
                    return new FindExchangeRateProviderException();
                });

        log.info("Got current exchange rate fromCurrency: {}, toCurrency: {}, provider: {}, exchangeRate: {}",
                fromCurrency, toCurrency,exchangeRate.getProviderName(), exchangeRate);

        return exchangeRate;
    }

}
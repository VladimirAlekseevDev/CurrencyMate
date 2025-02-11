package dev.sgd.currencymate.adapteralphavantage;

import dev.sgd.currencymate.adapteralphavantage.client.AlphavantageClient;
import dev.sgd.currencymate.adapteralphavantage.mapper.ExchangeRateMapper;
import dev.sgd.currencymate.adapteralphavantage.model.ExchangeRateResponse;
import dev.sgd.currencymate.domain.adapter.ExchangeRateAdapter;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlphavantageAdapter implements ExchangeRateAdapter {

    private final AlphavantageClient client;

    @Value("${app.adapter.alphavantage.apiKey}")
    private String apiKey;

    @Override
    @Retryable(
        retryFor = RuntimeException.class,
        maxAttemptsExpression = "${app.adapter.alphavantage.retry.attempts}",
        backoff = @Backoff(delayExpression = "${app.adapter.alphavantage.retry.delay}"))
    public ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) {
        // TODO add logging of attempt count: RetrySynchronizationManager.getContext().getRetryCount()

        try {
            ExchangeRateResponse response =
                client.getExchangeRate("CURRENCY_EXCHANGE_RATE", fromCurrency, toCurrency, apiKey);

            return ExchangeRateMapper.INSTANCE.toDomain(response);
        } catch (Exception e) {
            // TODO add logging
            throw new RuntimeException("Error in adapter", e);
        }
    }

}
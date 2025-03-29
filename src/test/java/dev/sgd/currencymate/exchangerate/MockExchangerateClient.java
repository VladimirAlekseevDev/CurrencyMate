package dev.sgd.currencymate.exchangerate;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sgd.currencymate.exchangerate.client.ExchangerateClient;
import dev.sgd.currencymate.exchangerate.model.AllCurrenciesResponse;
import dev.sgd.currencymate.exchangerate.model.ExchangeRateResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@RequiredArgsConstructor
public class MockExchangerateClient implements ExchangerateClient {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public AllCurrenciesResponse getAllCurrencies(String apiKey) {
        Resource currenciesResource = new ClassPathResource("adapter/exchangerate/currencies.json");
        return objectMapper.readValue(currenciesResource.getInputStream(), AllCurrenciesResponse.class);
    }

    @Override
    public ExchangeRateResponse getExchangeRate(String apiKey, String fromCurrency, String toCurrency) {
        return null;
    }

}
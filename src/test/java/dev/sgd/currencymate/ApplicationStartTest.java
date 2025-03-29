package dev.sgd.currencymate;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sgd.currencymate.exchangerate.client.ExchangerateClient;
import dev.sgd.currencymate.exchangerate.model.AllCurrenciesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class ApplicationStartTest {

    @MockitoBean
    private ExchangerateClient exchangerateClient;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() throws Exception {
        Resource currenciesResource = new ClassPathResource("adapter/exchangerate/currencies.json");
        AllCurrenciesResponse mockExchangerateCurrencies = objectMapper.readValue(currenciesResource.getInputStream(), AllCurrenciesResponse.class);

        when(exchangerateClient.getAllCurrencies(anyString()))
                .thenReturn(mockExchangerateCurrencies);
    }

    @Test
    void contextLoads() {}

}
package dev.sgd.currencymate.adapteralphavantage.model.exchangerate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExchangeRateResponse {

    @JsonProperty("Realtime Currency Exchange Rate")
    private ExchangeRateDto exchangeRate;

}
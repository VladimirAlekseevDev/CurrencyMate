package dev.sgd.currencymate.alphavantage.model.exchangerate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponse {

    @JsonProperty("Realtime Currency Exchange Rate")
    private ExchangeRateDto exchangeRate;

}
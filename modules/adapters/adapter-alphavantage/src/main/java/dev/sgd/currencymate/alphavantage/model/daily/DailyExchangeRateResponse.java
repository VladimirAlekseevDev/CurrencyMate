package dev.sgd.currencymate.alphavantage.model.daily;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sgd.currencymate.alphavantage.model.ExchangeRateValuesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyExchangeRateResponse {

    @JsonProperty("Meta Data")
    private DailyExchangeRateMetadataDto metadata;

    @JsonProperty("Time Series FX (Daily)")
    private Map<LocalDate, ExchangeRateValuesDto> timeSeries;


}
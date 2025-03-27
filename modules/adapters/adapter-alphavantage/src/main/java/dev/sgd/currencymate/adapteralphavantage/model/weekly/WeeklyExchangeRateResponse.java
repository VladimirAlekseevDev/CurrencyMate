package dev.sgd.currencymate.adapteralphavantage.model.weekly;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sgd.currencymate.adapteralphavantage.model.ExchangeRateValuesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyExchangeRateResponse {

    @JsonProperty("Meta Data")
    private WeeklyExchangeRateMetadataDto metadata;

    @JsonProperty("Time Series FX (Weekly)")
    private Map<LocalDate, ExchangeRateValuesDto> timeSeries;

}
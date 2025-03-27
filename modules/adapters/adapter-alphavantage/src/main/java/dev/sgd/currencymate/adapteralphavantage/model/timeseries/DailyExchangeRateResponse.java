package dev.sgd.currencymate.adapteralphavantage.model.timeseries;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private Map<LocalDate, DailyExchangeRateValuesDto> timeSeries;


}
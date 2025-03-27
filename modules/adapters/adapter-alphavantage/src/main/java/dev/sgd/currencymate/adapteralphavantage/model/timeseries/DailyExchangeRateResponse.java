package dev.sgd.currencymate.adapteralphavantage.model.timeseries;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyExchangeRateResponse {

    @JsonProperty("Meta Data")
    private DailyExchangeRateMetadataDto metadata;

    @JsonProperty("Time Series FX (Daily)")
    private Map<LocalDate, DailyExchangeRateValuesDto> timeSeries;


}
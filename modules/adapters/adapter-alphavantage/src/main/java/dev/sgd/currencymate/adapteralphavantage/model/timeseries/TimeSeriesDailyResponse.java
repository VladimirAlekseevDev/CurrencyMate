package dev.sgd.currencymate.adapteralphavantage.model.timeseries;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeSeriesDailyResponse {

    @JsonProperty("Meta Data")
    private TimeSeriesMetadataDto metadata;

    @JsonProperty("Time Series FX (Daily)")
    private Map<LocalDate, TimeSeriesExchangeRateDto> timeSeries;


}
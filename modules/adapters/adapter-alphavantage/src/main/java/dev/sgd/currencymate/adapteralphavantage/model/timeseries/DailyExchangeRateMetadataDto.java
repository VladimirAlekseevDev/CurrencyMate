package dev.sgd.currencymate.adapteralphavantage.model.timeseries;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyExchangeRateMetadataDto {

    @JsonProperty("1. Information")
    private String information;

    @JsonProperty("2. From Symbol")
    private String fromSymbol;

    @JsonProperty("3. To Symbol")
    private String toSymbol;

    @JsonProperty("4. Output Size")
    private String outputSize;

    @JsonProperty("5. Last Refreshed")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastRefreshed;

    @JsonProperty("6. Time Zone")
    private String timeZone;

}
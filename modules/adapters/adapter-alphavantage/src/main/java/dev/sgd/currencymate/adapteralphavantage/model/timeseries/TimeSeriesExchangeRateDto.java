package dev.sgd.currencymate.adapteralphavantage.model.timeseries;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeSeriesExchangeRateDto {

    @JsonProperty("1. open")
    private BigDecimal open;

    @JsonProperty("2. high")
    private BigDecimal high;

    @JsonProperty("3. low")
    private BigDecimal low;

    @JsonProperty("4. close")
    private BigDecimal close;

}
package dev.sgd.currencymate.rest.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Open/High/Low/Close exchange rate values for a specific point in time")
public class ExchangeRateValuesDto {

    @Schema(description = "Opening rate")
    private BigDecimal open;

    @Schema(description = "Highest rate")
    private BigDecimal high;

    @Schema(description = "Lowest rate")
    private BigDecimal low;

    @Schema(description = "Closing rate")
    private BigDecimal close;

}
package dev.sgd.currencymate.rest.api.model.response;

import dev.sgd.currencymate.rest.api.model.CurrencyDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Exchange rate information between two currencies")
public class ExchangeRateResponse {

    @Schema(description = "From currency details")
    private CurrencyDto from;

    @Schema(description = "To currency details")
    private CurrencyDto to;

    @Schema(description = "Name of the currency exchange data provider")
    private String providerName;

    @Schema(description = "Exchange rate value")
    private BigDecimal rate;

    @Schema(description = "Timestamp when data was last refreshed by provider")
    private OffsetDateTime lastRefreshed;

    @Schema(description = "Timestamp when data was received by Currency Mate application")
    private OffsetDateTime receivedAt;

}
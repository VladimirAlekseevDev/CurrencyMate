package dev.sgd.currencymate.rest.api.model.response;

import dev.sgd.currencymate.rest.api.model.CurrencyDto;
import dev.sgd.currencymate.rest.api.model.ExchangeRateValuesDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Daily exchange rate response")
public class DailyExchangeRateResponse {

    @Schema(description = "Additional information")
    private String info;

    @Schema(description = "From currency details")
    private CurrencyDto from;

    @Schema(description = "To currency details")
    private CurrencyDto to;

    @Schema(description = "Name of the data provider")
    private String providerName;

    @Schema(description = "Timestamp when data was last refreshed by provider")
    private OffsetDateTime lastRefreshed;

    @Schema(description = "Timestamp when data was received by Currency Mate application")
    private OffsetDateTime receivedAt;

    @Schema(description = "Daily exchange rate values mapped by date")
    private Map<LocalDate, ExchangeRateValuesDto> exchangeRateValues;
}
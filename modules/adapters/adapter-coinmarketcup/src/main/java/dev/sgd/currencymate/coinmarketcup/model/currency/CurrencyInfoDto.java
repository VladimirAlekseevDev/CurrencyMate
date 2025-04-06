package dev.sgd.currencymate.coinmarketcup.model.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sgd.currencymate.coinmarketcup.model.PlatformDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyInfoDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("name")
    private String name;

    // User for CRYPTO currencies
    @JsonProperty("symbol")
    private String symbol;

    // Used for FIAT currencies
    @JsonProperty("sign")
    private String sign;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("is_active")
    private int isActive;

    @JsonProperty("status")
    private int status;

    @JsonProperty("first_historical_data")
    private OffsetDateTime firstHistoricalData;

    @JsonProperty("last_historical_data")
    private OffsetDateTime lastHistoricalData;

    @JsonProperty("platform")
    private PlatformDto platform;

}
package dev.sgd.currencymate.coinmarketcup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateValueDto {

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("volume_24h")
    private BigDecimal volume24h;

    @JsonProperty("volume_change_24h")
    private BigDecimal volumeChange24h;

    @JsonProperty("percent_change_1h")
    private BigDecimal percentChange1h;

    @JsonProperty("percent_change_24h")
    private BigDecimal percentChange24h;

    @JsonProperty("percent_change_7d")
    private BigDecimal percentChange7d;

    @JsonProperty("percent_change_30d")
    private BigDecimal percentChange30d;

    @JsonProperty("percent_change_60d")
    private BigDecimal percentChange60d;

    @JsonProperty("percent_change_90d")
    private BigDecimal percentChange90d;

    @JsonProperty("market_cap")
    private BigDecimal marketCap;

    @JsonProperty("market_cap_dominance")
    private BigDecimal marketCapDominance;

    @JsonProperty("fully_diluted_market_cap")
    private BigDecimal fullyDilutedMarketCap;

    @JsonProperty("tvl")
    private BigDecimal tvl;

    @JsonProperty("last_updated")
    private OffsetDateTime lastUpdated;

}
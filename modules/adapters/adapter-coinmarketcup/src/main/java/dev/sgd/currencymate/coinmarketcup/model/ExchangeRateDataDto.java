package dev.sgd.currencymate.coinmarketcup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDataDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("num_market_pairs")
    private int numMarketPairs;

    @JsonProperty("date_added")
    private OffsetDateTime dateAdded;

    @JsonProperty("tags")
    private List<ExchangeRateTagDto> tags;

    @JsonProperty("max_supply")
    private BigDecimal maxSupply;

    @JsonProperty("circulating_supply")
    private BigDecimal circulatingSupply;

    @JsonProperty("total_supply")
    private BigDecimal totalSupply;

    @JsonProperty("is_active")
    private int isActive;

    @JsonProperty("infinite_supply")
    private boolean infiniteSupply;

    @JsonProperty("platform")
    private PlatformDto platform;

    @JsonProperty("cmc_rank")
    private Integer cmcRank;

    @JsonProperty("is_fiat")
    private Integer isFiat;

    @JsonProperty("self_reported_circulating_supply")
    private BigDecimal selfReportedCirculatingSupply;

    @JsonProperty("self_reported_market_cap")
    private BigDecimal selfReportedMarketCap;

    @JsonProperty("tvl_ratio")
    private BigDecimal tvlRatio;

    @JsonProperty("last_updated")
    private OffsetDateTime lastUpdated;

    @JsonProperty("quote")
    private Map<String, ExchangeRateValueDto> quote;

}
package dev.sgd.currencymate.adapteralphavantage.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExchangeRateDto {

    @JsonProperty("1. From_Currency Code")
    private String fromCurrencyCode;

    @JsonProperty("2. From_Currency Name")
    private String fromCurrencyName;

    @JsonProperty("3. To_Currency Code")
    private String toCurrencyCode;

    @JsonProperty("4. To_Currency Name")
    private String toCurrencyName;

    @JsonProperty("5. Exchange Rate")
    private BigDecimal exchangeRate;

    @JsonProperty("6. Last Refreshed")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastRefreshed;

    @JsonProperty("7. Time Zone")
    private String timeZone;

    @JsonProperty("8. Bid Price")
    private BigDecimal bidPrice;

    @JsonProperty("9. Ask Price")
    private BigDecimal askPrice;

}
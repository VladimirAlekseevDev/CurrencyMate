package dev.sgd.currencymate.adapteralphavantage.model.exchangerate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
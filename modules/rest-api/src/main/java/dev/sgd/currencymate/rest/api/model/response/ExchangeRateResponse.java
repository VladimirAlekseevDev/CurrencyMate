package dev.sgd.currencymate.rest.api.model.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import dev.sgd.currencymate.rest.api.model.CurrencyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponse {

    private CurrencyDto from;

    private CurrencyDto to;

    private String providerName;

    private BigDecimal rate;

    private OffsetDateTime lastRefreshed;

    private OffsetDateTime receivedAt;

}
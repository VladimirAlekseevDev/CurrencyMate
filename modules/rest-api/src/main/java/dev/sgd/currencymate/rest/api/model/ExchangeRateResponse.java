package dev.sgd.currencymate.rest.api.model;

import dev.sgd.currencymate.domain.model.Currency;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class ExchangeRateResponse {

    private Currency from;

    private Currency to;

    private String providerName;

    private BigDecimal rate;

    private OffsetDateTime lastRefreshed;

    private OffsetDateTime receivedAt;

}
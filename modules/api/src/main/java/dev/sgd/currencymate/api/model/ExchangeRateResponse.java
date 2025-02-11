package dev.sgd.currencymate.api.model;

import dev.sgd.currencymate.domain.model.Currency;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class ExchangeRateResponse {

    private Currency from;

    private Currency to;

    private BigDecimal rate;

    private OffsetDateTime lastRefreshed;

    private OffsetDateTime receivedAt;

}
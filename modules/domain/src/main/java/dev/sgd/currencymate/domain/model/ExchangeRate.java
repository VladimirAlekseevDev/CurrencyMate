package dev.sgd.currencymate.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class ExchangeRate {

    private Currency from;

    private Currency to;

    private BigDecimal rate;

    private OffsetDateTime lastRefreshed;

    private OffsetDateTime receivedAt;

}
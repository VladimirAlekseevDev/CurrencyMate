package dev.sgd.currencymate.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {

    private Currency from;

    private Currency to;

    private String providerName;

    private BigDecimal rate;

    private OffsetDateTime lastRefreshed;

    private OffsetDateTime receivedAt;

}
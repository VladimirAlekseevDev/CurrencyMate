package dev.sgd.currencymate.domain.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDaily {

    private String info;

    private Currency from;

    private Currency to;

    private String providerName;

    private OffsetDateTime lastRefreshed;

    private OffsetDateTime receivedAt;

    private Map<LocalDate, TimeSeriesExchangeRate> exchangeRateTimeSeries;

}
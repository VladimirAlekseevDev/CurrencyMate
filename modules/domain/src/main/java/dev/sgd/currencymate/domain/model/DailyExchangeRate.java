package dev.sgd.currencymate.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyExchangeRate {

    private String info;

    private Currency from;

    private Currency to;

    private String providerName;

    private OffsetDateTime lastRefreshed;

    private OffsetDateTime receivedAt;

    private Map<LocalDate, ExchangeRateValues> exchangeRateTimeSeries;

}
package dev.sgd.currencymate.rest.api.model.response;

import dev.sgd.currencymate.rest.api.model.CurrencyDto;
import dev.sgd.currencymate.rest.api.model.DailyExchangeRateValuesDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyExchangeRateResponse {

    private String info;

    private CurrencyDto from;

    private CurrencyDto to;

    private String providerName;

    private OffsetDateTime lastRefreshed;

    private OffsetDateTime receivedAt;

    private Map<LocalDate, DailyExchangeRateValuesDto> exchangeRateTimeSeries;

}

package dev.sgd.currencymate.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.sgd.currencymate.exchangerate.config.ExchangerateDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponse {

    private String result;

    private String documentation;

    @JsonProperty("terms_of_use")
    private String termsOfUse;

    @JsonProperty("time_last_update_unix")
    private Long timeLastUpdateUnix;

    @JsonProperty("time_last_update_utc")
    @JsonDeserialize(using = ExchangerateDateTimeDeserializer.class)
    private OffsetDateTime timeLastUpdateUtc;

    @JsonProperty("time_next_update_unix")
    private Long timeNextUpdateUnix;

    @JsonProperty("time_next_update_utc")
    @JsonDeserialize(using = ExchangerateDateTimeDeserializer.class)
    private OffsetDateTime timeNextUpdateUtc;

    @JsonProperty("base_code")
    private String baseCode;

    @JsonProperty("target_code")
    private String targetCode;

    @JsonProperty("conversion_rate")
    private BigDecimal conversionRate;

}
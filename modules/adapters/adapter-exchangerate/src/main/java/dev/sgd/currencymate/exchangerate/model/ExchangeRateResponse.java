package dev.sgd.currencymate.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

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
    @JsonFormat(pattern = "EEE, dd MMM yyyy HH:mm:ss Z", shape = STRING)
    private OffsetDateTime timeLastUpdateUtc;

    @JsonProperty("time_next_update_unix")
    private Long timeNextUpdateUnix;

    @JsonProperty("time_next_update_utc")
    @JsonFormat(pattern = "EEE, dd MMM yyyy HH:mm:ss Z", shape = STRING)
    private OffsetDateTime timeNextUpdateUtc;

    @JsonProperty("base_code")
    private String baseCode;

    @JsonProperty("target_code")
    private String targetCode;

    @JsonProperty("conversion_rate")
    private BigDecimal conversionRate;

}
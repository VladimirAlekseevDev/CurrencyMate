package dev.sgd.currencymate.coinmarketcup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDto {

    @JsonProperty("timestamp")
    private OffsetDateTime timestamp;

    @JsonProperty("error_code")
    private int errorCode;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("elapsed")
    private int elapsed;

    @JsonProperty("credit_count")
    private int creditCount;

    @JsonProperty("notice")
    private String notice;

}
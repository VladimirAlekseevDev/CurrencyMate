package dev.sgd.currencymate.domain.error;

import lombok.Getter;

@Getter
public enum ErrorEnum {

    UNKNOWN_ERROR("Unknown error occurred"),
    ADAPTER_ERROR("Error occurred in adapter"),
    REQUEST_VALIDATION_ERROR("Request parameters are invalid");

    private final String message;
    private final String code;

    ErrorEnum(String message) {
        this.message = message;
        this.code = this.name();
    }

}
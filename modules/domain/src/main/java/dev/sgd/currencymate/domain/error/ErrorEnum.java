package dev.sgd.currencymate.domain.error;

import lombok.Getter;

@Getter
public enum ErrorEnum {

    /* Common */
    UNKNOWN_ERROR("Internal exception occurred while performing the operation, please try again later"),
    REQUEST_VALIDATION_ERROR("Request parameters are not valid, check the API specs"),
    ADAPTER_ERROR("Exception occurred while calling another service, please try again later"),

    /* More specific */
    FIND_EXCHANGE_RATE_PROVIDER_ERROR(
            """
            We don't yet have a suitable provider for the requested currencies 😟
            Please try using other currencies
            """
    );


    private final String message;
    private final String code;

    ErrorEnum(String message) {
        this.message = message;
        this.code = this.name();
    }

}
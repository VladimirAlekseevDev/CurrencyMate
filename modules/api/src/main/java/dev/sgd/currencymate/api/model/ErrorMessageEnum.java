package dev.sgd.currencymate.api.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorMessageEnum {

    DEFAULT(""),
    ADAPTER_EXCEPTION("Adapter exception");


    private final String message;

}
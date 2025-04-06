package dev.sgd.currencymate.domain.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseException extends RuntimeException {

    public abstract ErrorEnum getError();

}
package dev.sgd.currencymate.domain.error.common;

import dev.sgd.currencymate.domain.error.ErrorEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BadRequestException extends RuntimeException {

    public ErrorEnum getError() {
        return ErrorEnum.REQUEST_VALIDATION_ERROR;
    }

}
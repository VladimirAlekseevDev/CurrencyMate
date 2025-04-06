package dev.sgd.currencymate.domain.error.common;

import dev.sgd.currencymate.domain.error.BaseException;
import dev.sgd.currencymate.domain.error.ErrorEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InternalException extends BaseException {

    @Override
    public ErrorEnum getError() {
        return ErrorEnum.UNKNOWN_ERROR;
    }

}
package dev.sgd.currencymate.domain.error.common;

import dev.sgd.currencymate.domain.error.BaseException;
import dev.sgd.currencymate.domain.error.ErrorEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdapterException extends BaseException {

    @Override
    public ErrorEnum getError() {
        return ErrorEnum.ADAPTER_ERROR;
    }

}
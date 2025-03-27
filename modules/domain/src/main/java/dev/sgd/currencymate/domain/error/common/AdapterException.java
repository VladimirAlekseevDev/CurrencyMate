package dev.sgd.currencymate.domain.error.common;

import dev.sgd.currencymate.domain.error.ErrorEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdapterException extends RuntimeException {

    public ErrorEnum getError() {
        return ErrorEnum.ADAPTER_ERROR;
    }

}
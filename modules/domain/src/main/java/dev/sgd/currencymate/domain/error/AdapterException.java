package dev.sgd.currencymate.domain.error;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdapterException extends RuntimeException {

    public ErrorEnum getError() {
        return ErrorEnum.ADAPTER_ERROR;
    }

}
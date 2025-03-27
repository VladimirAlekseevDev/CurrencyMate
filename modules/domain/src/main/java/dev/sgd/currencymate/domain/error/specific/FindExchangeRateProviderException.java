package dev.sgd.currencymate.domain.error.specific;

import dev.sgd.currencymate.domain.error.ErrorEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindExchangeRateProviderException extends RuntimeException {

    public ErrorEnum getError() {
        return ErrorEnum.FIND_EXCHANGE_RATE_PROVIDER_ERROR;
    }

}
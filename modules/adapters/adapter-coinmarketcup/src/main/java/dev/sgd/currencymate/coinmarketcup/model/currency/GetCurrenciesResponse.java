package dev.sgd.currencymate.coinmarketcup.model.currency;

import dev.sgd.currencymate.coinmarketcup.model.StatusDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCurrenciesResponse {

    private StatusDto status;

    private List<CurrencyInfoDto> data;

}
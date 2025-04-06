package dev.sgd.currencymate.coinmarketcup.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponse {

    private StatusDto status;

    private Map<String, List<ExchangeRateDataDto>> data;

}
package dev.sgd.currencymate.domain.model;

import dev.sgd.currencymate.domain.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    private String code;

    private String name;

    private CurrencyType type;

}
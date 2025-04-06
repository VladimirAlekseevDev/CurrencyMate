package dev.sgd.currencymate.coinmarketcup.model.currency;

import dev.sgd.currencymate.coinmarketcup.model.PlatformDto;
import dev.sgd.currencymate.domain.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyInfo {

    private long id;

    private CurrencyType type;

    private int rank;

    private String name;

    private String code;

    private String slug;

    private int isActive;

    private int status;

    private OffsetDateTime firstHistoricalData;

    private OffsetDateTime lastHistoricalData;

    private PlatformDto platform;

}
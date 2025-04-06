package dev.sgd.currencymate.rest.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Currency details")
public class CurrencyDto {

    @Schema(description = "Currency (crypto/fiat) code (USD, EUR, BTC, ETH etc.)")
    private String code;

    @Schema(description = "Full currency name (US Dollar, Euro, Bitcoin, Ethereum etc.)")
    private String name;

}
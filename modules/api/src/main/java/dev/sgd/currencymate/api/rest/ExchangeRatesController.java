package dev.sgd.currencymate.api.rest;

import dev.sgd.currencymate.api.mapper.ExchangeRateMapper;
import dev.sgd.currencymate.api.model.ExchangeRateResponse;
import dev.sgd.currencymate.domain.model.TimeSeries;
import dev.sgd.currencymate.services.ExchangeRatesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ExchangeRatesController {

    private final ExchangeRatesService exchangeRatesService;

    @GetMapping("/exchangeRate")
    public ResponseEntity<ExchangeRateResponse> getExchangeRate(
            @Valid @NotBlank @RequestParam(name = "fromCurrency") String fromCurrency,
            @Valid @NotBlank @RequestParam(name = "toCurrency") String toCurrency) {

        return ResponseEntity.ok(
            ExchangeRateMapper.INSTANCE.toApi(
                exchangeRatesService.getExchangeRate(fromCurrency, toCurrency)
            )
        );
    }

    @GetMapping("/exchangeRate/daily")
    public ResponseEntity<TimeSeries> getExchangeRateDaily(
            @Valid @NotBlank @RequestParam(name = "fromCurrency") String fromCurrency,
            @Valid @NotBlank @RequestParam(name = "toCurrency") String toCurrency) {

        return ResponseEntity.ok(
            exchangeRatesService.getExchangeRateDaily(fromCurrency, toCurrency)
        );
    }

}
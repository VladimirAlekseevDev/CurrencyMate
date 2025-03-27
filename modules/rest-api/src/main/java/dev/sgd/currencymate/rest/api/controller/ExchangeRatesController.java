package dev.sgd.currencymate.rest.api.controller;

import dev.sgd.currencymate.rest.api.mapper.ExchangeRateMapper;
import dev.sgd.currencymate.rest.api.model.ExchangeRateResponse;
import dev.sgd.currencymate.domain.model.TimeSeries;
import dev.sgd.currencymate.usecase.GetExchangeRateUseCase;
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

    private final GetExchangeRateUseCase getExchangeRateUseCase;

    @GetMapping("/exchangeRate/current")
    public ResponseEntity<ExchangeRateResponse> getCurrentExchangeRate(
            @Valid @NotBlank @RequestParam(name = "fromCurrency") String fromCurrency,
            @Valid @NotBlank @RequestParam(name = "toCurrency") String toCurrency) {

        return ResponseEntity.ok(
            ExchangeRateMapper.INSTANCE.toApi(
                getExchangeRateUseCase.getCurrentExchangeRate(fromCurrency, toCurrency)
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
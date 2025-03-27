package dev.sgd.currencymate.rest.api.controller;

import dev.sgd.currencymate.rest.api.model.response.DailyExchangeRateResponse;
import dev.sgd.currencymate.rest.api.model.response.ExchangeRateResponse;
import dev.sgd.currencymate.usecase.GetCurrentExchangeRateUseCase;
import dev.sgd.currencymate.usecase.GetDailyExchangeRatesUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static dev.sgd.currencymate.rest.api.mapper.DailyExchangeRateMapper.DAILY_EXCHANGE_RATE_MAPPER;
import static dev.sgd.currencymate.rest.api.mapper.ExchangeRateMapper.EXCHANGE_RATE_MAPPER;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ExchangeRatesControllerV1 {

    private final GetCurrentExchangeRateUseCase getCurrentExchangeRateUseCase;
    private final GetDailyExchangeRatesUseCase getDailyExchangeRateUseCase;

    @GetMapping("/exchangeRate/current")
    public ResponseEntity<ExchangeRateResponse> getCurrentExchangeRate(
            @Valid @NotBlank @RequestParam(name = "fromCurrency") String fromCurrency,
            @Valid @NotBlank @RequestParam(name = "toCurrency") String toCurrency) {

        ExchangeRateResponse response = EXCHANGE_RATE_MAPPER.toApi(
                getCurrentExchangeRateUseCase.getCurrentExchangeRate(fromCurrency, toCurrency));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/exchangeRate/daily")
    public ResponseEntity<DailyExchangeRateResponse> getDailyExchangeRate(
            @Valid @NotBlank @RequestParam(name = "fromCurrency") String fromCurrency,
            @Valid @NotBlank @RequestParam(name = "toCurrency") String toCurrency) {

        DailyExchangeRateResponse response = DAILY_EXCHANGE_RATE_MAPPER.toApi(
                getDailyExchangeRateUseCase.getDailyExchangeRate(fromCurrency, toCurrency));

        return ResponseEntity.ok(response);
    }

}
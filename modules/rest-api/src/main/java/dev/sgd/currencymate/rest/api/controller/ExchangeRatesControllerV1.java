package dev.sgd.currencymate.rest.api.controller;

import dev.sgd.currencymate.rest.api.model.error.ErrorResponse;
import dev.sgd.currencymate.rest.api.model.response.DailyExchangeRateResponse;
import dev.sgd.currencymate.rest.api.model.response.ExchangeRateResponse;
import dev.sgd.currencymate.rest.api.model.response.WeeklyExchangeRateResponse;
import dev.sgd.currencymate.usecase.GetCurrentExchangeRateUseCase;
import dev.sgd.currencymate.usecase.GetDailyExchangeRatesUseCase;
import dev.sgd.currencymate.usecase.GetWeeklyExchangeRatesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static dev.sgd.currencymate.rest.api.mapper.DailyExchangeRateResponseMapper.DAILY_EXCHANGE_RATE_RESPONSE_MAPPER;
import static dev.sgd.currencymate.rest.api.mapper.ExchangeRateMapper.EXCHANGE_RATE_MAPPER;
import static dev.sgd.currencymate.rest.api.mapper.WeeklyExchangeRateResponseMapper.WEEKLY_EXCHANGE_RATE_RESPONSE_MAPPER;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Exchange Rates", description = "Получение текущих и исторических курсов валют")
public class ExchangeRatesControllerV1 {

    private final GetCurrentExchangeRateUseCase getCurrentExchangeRateUseCase;
    private final GetDailyExchangeRatesUseCase getDailyExchangeRateUseCase;
    private final GetWeeklyExchangeRatesUseCase getWeeklyExchangeRateUseCase;

    @Operation(summary = "Get current currency (crypto/fiat) exchange rate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Exchange rate successfully received",
                         content = @Content(schema = @Schema(implementation = ExchangeRateResponse.class))),
            @ApiResponse(responseCode = "400",
                         description = "Bad request",
                         content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/exchangeRate/current")
    public ResponseEntity<ExchangeRateResponse> getCurrentExchangeRate(
            @RequestParam @Parameter(description = "From currency") @Valid @NotBlank String fromCurrency,
            @RequestParam @Parameter(description = "To currency") @Valid @NotBlank String toCurrency) {

        ExchangeRateResponse response = EXCHANGE_RATE_MAPPER.toApi(
                getCurrentExchangeRateUseCase.getCurrentExchangeRate(fromCurrency, toCurrency));

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get currency (crypto/fiat) exchange rate daily time series")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Exchange rate successfully received",
                    content = @Content(schema = @Schema(implementation = ExchangeRateResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/exchangeRate/daily")
    public ResponseEntity<DailyExchangeRateResponse> getDailyExchangeRate(
            @RequestParam @Parameter(description = "From currency") @Valid @NotBlank String fromCurrency,
            @RequestParam @Parameter(description = "To currency") @Valid @NotBlank String toCurrency) {

        DailyExchangeRateResponse response = DAILY_EXCHANGE_RATE_RESPONSE_MAPPER.toApi(
                getDailyExchangeRateUseCase.getDailyExchangeRate(fromCurrency, toCurrency));

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get currency (crypto/fiat) exchange rate weekly time series")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Exchange rate successfully received",
                    content = @Content(schema = @Schema(implementation = ExchangeRateResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/exchangeRate/weekly")
    public ResponseEntity<WeeklyExchangeRateResponse> getWeeklyExchangeRate(
            @RequestParam @Parameter(description = "From currency") @Valid @NotBlank String fromCurrency,
            @RequestParam @Parameter(description = "To currency") @Valid @NotBlank String toCurrency) {

        WeeklyExchangeRateResponse response = WEEKLY_EXCHANGE_RATE_RESPONSE_MAPPER.toApi(
                getWeeklyExchangeRateUseCase.getWeeklyExchangeRate(fromCurrency, toCurrency));

        return ResponseEntity.ok(response);
    }
}

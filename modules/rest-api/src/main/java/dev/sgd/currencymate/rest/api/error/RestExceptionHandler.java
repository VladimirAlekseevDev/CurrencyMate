package dev.sgd.currencymate.rest.api.error;

import dev.sgd.currencymate.domain.error.ErrorEnum;
import dev.sgd.currencymate.domain.error.common.AdapterException;
import dev.sgd.currencymate.domain.error.common.BadRequestException;
import dev.sgd.currencymate.domain.error.common.ExternalServiceException;
import dev.sgd.currencymate.domain.error.common.InternalException;
import dev.sgd.currencymate.domain.error.specific.FindExchangeRateProviderException;
import dev.sgd.currencymate.rest.api.model.error.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import static dev.sgd.currencymate.domain.error.ErrorEnum.REQUEST_VALIDATION_ERROR;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            ErrorEnum.UNKNOWN_ERROR.getMessage(),
            ErrorEnum.UNKNOWN_ERROR.getCode());

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ErrorResponse> handleInternalException(InternalException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getError().getMessage(),
            ex.getError().getCode());

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse > handleBadRequestException(BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getError().getMessage(),
                ex.getError().getCode()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(AdapterException.class)
    public ResponseEntity<ErrorResponse> handleAdapterException(AdapterException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getError().getMessage(),
            ex.getError().getCode());

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ErrorResponse> handleExternalServiceException(ExternalServiceException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getError().getMessage(),
            ex.getError().getCode());

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(FindExchangeRateProviderException.class)
    public ResponseEntity<ErrorResponse> handleFindExchangeRateProviderException(FindExchangeRateProviderException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getError().getMessage(),
            ex.getError().getCode());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations()
            .stream()
            .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
            .reduce((msg1, msg2) -> msg1 + ", " + msg2)
            .orElse("Validation error");

        ErrorResponse errorResponse = new ErrorResponse(
            errorMessage,
            REQUEST_VALIDATION_ERROR.getCode());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .reduce((msg1, msg2) -> msg1 + ", " + msg2)
            .orElse("Validation error");

        ErrorResponse errorResponse = new ErrorResponse(
            errorMessage,
            REQUEST_VALIDATION_ERROR.getCode());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getMessage(),
            REQUEST_VALIDATION_ERROR.getCode());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            REQUEST_VALIDATION_ERROR.getMessage(),
            REQUEST_VALIDATION_ERROR.getCode());

        return ResponseEntity.badRequest().body(errorResponse);
        }

}
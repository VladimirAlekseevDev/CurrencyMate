package dev.sgd.currencymate.api.error;

import static dev.sgd.currencymate.domain.error.ErrorEnum.REQUEST_VALIDATION_ERROR;

import dev.sgd.currencymate.api.model.ErrorResponse;
import dev.sgd.currencymate.domain.error.AdapterException;
import dev.sgd.currencymate.domain.error.ErrorEnum;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    // Обработка общего исключения RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            ErrorEnum.UNKNOWN_ERROR.getMessage(),
            ErrorEnum.UNKNOWN_ERROR.getCode());

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(AdapterException.class)
    public ResponseEntity<ErrorResponse> handleAdapterException(AdapterException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getError().getMessage(),
            ex.getError().getCode());

        return ResponseEntity.internalServerError().body(errorResponse);
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
            "REQUEST_VALIDATION_ERROR");

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getMessage(),
            "REQUEST_VALIDATION_ERROR");

        return ResponseEntity.badRequest().body(errorResponse);
    }

}
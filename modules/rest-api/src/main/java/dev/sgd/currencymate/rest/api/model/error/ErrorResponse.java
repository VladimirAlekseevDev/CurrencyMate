package dev.sgd.currencymate.rest.api.model.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Error response DTO")
public class ErrorResponse {

    @Schema(description = "Error human readable message")
    private String message;

    @Schema(description = "Error text code")
    private String code;

}
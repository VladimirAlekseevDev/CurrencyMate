package dev.sgd.currencymate.adapteralphavantage.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sgd.currencymate.adapteralphavantage.model.ErrorResponse;
import dev.sgd.currencymate.domain.error.common.AdapterException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@DependsOn("feignLogger")
public class AlphavantageFeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;
    private final Logger logger;

    public AlphavantageFeignErrorDecoder(ObjectMapper objectMapper,
                                         @Qualifier("feignLogger") Logger logger) {
        this.objectMapper = objectMapper;
        this.logger = logger;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            InputStream bodyInputStream = response.body().asInputStream();
            ErrorResponse errorResponse = objectMapper.readValue(bodyInputStream, ErrorResponse.class);

            if (bodyInputStream != null && errorResponse != null) {
                logger.error("Error calling alphavantage API, methodKey={}, status={}, reason='{}', error={}",
                        methodKey, response.status(), response.reason(), errorResponse);
            } else {
                logger.error("Error calling alphavantage API, response body is empty, methodKey={}, status={}, reason='{}'",
                        methodKey, response.status(), response.reason());
            }
        } catch (Exception e) {
            logger.error("Error parsing response body from alphavantage API, methodKey={}, status={}, reason='{}', error: {}",
                    methodKey, response.status(), response.reason(), e.getMessage());
        }

        HttpStatus httpStatus = HttpStatus.valueOf(response.status());
        if (httpStatus.is4xxClientError()) {
            return new AdapterException();
        } else {
            return new feign.RetryableException(
                    response.status(),
                    "Error calling Alphavantage API",
                    response.request().httpMethod(),
                    (Long) null,
                    response.request()
            );
        }
    }

}
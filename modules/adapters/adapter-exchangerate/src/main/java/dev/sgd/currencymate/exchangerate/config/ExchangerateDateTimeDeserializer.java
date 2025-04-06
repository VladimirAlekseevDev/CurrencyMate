package dev.sgd.currencymate.exchangerate.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.SneakyThrows;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ofPattern;

public class ExchangerateDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

    private static final DateTimeFormatter FORMATTER =
            ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

    @Override
    @SneakyThrows
    public OffsetDateTime deserialize(JsonParser parser, DeserializationContext context) {
        String value = parser.getText();

        if (value != null) {
            return OffsetDateTime.parse(value, FORMATTER);
        } else {
            return null;
        }
    }

}
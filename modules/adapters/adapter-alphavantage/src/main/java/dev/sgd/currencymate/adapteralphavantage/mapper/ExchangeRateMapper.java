package dev.sgd.currencymate.adapteralphavantage.mapper;

import dev.sgd.currencymate.adapteralphavantage.model.exchangerate.ExchangeRateResponse;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.domain.utils.DateTimeUtils;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExchangeRateMapper {

    ExchangeRateMapper INSTANCE = Mappers.getMapper(ExchangeRateMapper.class);

    @Mapping(target = "from.code", source = "exchangeRate.fromCurrencyCode")
    @Mapping(target = "from.name", source = "exchangeRate.fromCurrencyName")
    @Mapping(target = "to.code", source = "exchangeRate.toCurrencyCode")
    @Mapping(target = "to.name", source = "exchangeRate.toCurrencyName")
    @Mapping(target = "rate", source = "exchangeRate.exchangeRate")
    @Mapping(target = "lastRefreshed",
        expression = "java(mapToOffsetDateTime(response.getExchangeRate().getLastRefreshed(), response.getExchangeRate().getTimeZone()))")
    @Mapping(target = "receivedAt", ignore = true)
    ExchangeRate toDomain(ExchangeRateResponse response);

    default OffsetDateTime mapToOffsetDateTime(LocalDateTime lastRefreshed, String timeZone) {
        if (lastRefreshed == null) {
            return null;
        }
        if (timeZone == null) {
            return DateTimeUtils.toOffsetDateTime(lastRefreshed);
        }

        try {
            return DateTimeUtils.toOffsetDateTime(lastRefreshed, timeZone);
        } catch (Exception e) {
            // TODO add logging
            throw new IllegalArgumentException("Incorrect time zone: " + timeZone, e);
        }
    }

}
package dev.sgd.currencymate.adapteralphavantage.mapper;

import dev.sgd.currencymate.adapteralphavantage.model.ExchangeRateResponse;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
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
            return lastRefreshed.atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }

        try {
            return lastRefreshed.atZone(ZoneId.of(timeZone)).toOffsetDateTime();
        } catch (Exception e) {
            // TODO add logging
            throw new IllegalArgumentException("Incorrect time zone: " + timeZone, e);
        }
    }

}
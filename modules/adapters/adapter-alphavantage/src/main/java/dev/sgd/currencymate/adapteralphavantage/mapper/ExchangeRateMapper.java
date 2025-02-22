package dev.sgd.currencymate.adapteralphavantage.mapper;

import dev.sgd.currencymate.adapteralphavantage.model.exchangerate.ExchangeRateResponse;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { DateTimeMapper.class })
public interface ExchangeRateMapper {

    ExchangeRateMapper INSTANCE = Mappers.getMapper(ExchangeRateMapper.class);

    @Mapping(target = "from.code", source = "exchangeRate.fromCurrencyCode")
    @Mapping(target = "from.name", source = "exchangeRate.fromCurrencyName")
    @Mapping(target = "to.code", source = "exchangeRate.toCurrencyCode")
    @Mapping(target = "to.name", source = "exchangeRate.toCurrencyName")
    @Mapping(target = "rate", source = "exchangeRate.exchangeRate")
    @Mapping(target = "lastRefreshed",
        expression = "java(DateTimeMapper.INSTANCE.mapToOffsetDateTime(api.getExchangeRate().getLastRefreshed(), api.getExchangeRate().getTimeZone()))")
    @Mapping(target = "receivedAt", ignore = true)
    ExchangeRate toDomain(ExchangeRateResponse api);

}
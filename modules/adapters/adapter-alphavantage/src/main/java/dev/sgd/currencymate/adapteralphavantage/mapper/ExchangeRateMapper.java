package dev.sgd.currencymate.adapteralphavantage.mapper;

import dev.sgd.currencymate.adapteralphavantage.model.exchangerate.ExchangeRateResponse;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { DateTimeMapper.class })
public interface ExchangeRateMapper {

    ExchangeRateMapper EXCHANGE_RATE_MAPPER = Mappers.getMapper(ExchangeRateMapper.class);

    @Mapping(target = "from.code", source = "exchangeRate.fromCurrencyCode")
    @Mapping(target = "from.name", source = "exchangeRate.fromCurrencyName")
    @Mapping(target = "to.code", source = "exchangeRate.toCurrencyCode")
    @Mapping(target = "to.name", source = "exchangeRate.toCurrencyName")
    @Mapping(target = "rate", source = "exchangeRate.exchangeRate")
    @Mapping(target = "lastRefreshed",
        expression = "java(DateTimeMapper.INSTANCE.mapToOffsetDateTime(api.getExchangeRate().getLastRefreshed(), api.getExchangeRate().getTimeZone()))")
    @Mapping(target = "receivedAt", expression = "java(dev.sgd.currencymate.domain.utils.DateTimeUtils;)")
    @Mapping(target = "providerName", ignore = true)
    ExchangeRate toDomain(ExchangeRateResponse api);

}
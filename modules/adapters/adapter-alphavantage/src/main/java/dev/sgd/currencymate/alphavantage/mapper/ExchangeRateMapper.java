package dev.sgd.currencymate.alphavantage.mapper;

import dev.sgd.currencymate.alphavantage.config.DefaultMapperConfig;
import dev.sgd.currencymate.alphavantage.model.exchangerate.ExchangeRateResponse;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(config = DefaultMapperConfig.class,
        uses = { DateTimeMapper.class })
public interface ExchangeRateMapper {

    ExchangeRateMapper EXCHANGE_RATE_MAPPER = getMapper(ExchangeRateMapper.class);

    @Mapping(target = "from.code", source = "exchangeRate.fromCurrencyCode")
    @Mapping(target = "from.name", source = "exchangeRate.fromCurrencyName")
    @Mapping(target = "from.type", ignore = true)
    @Mapping(target = "to.code", source = "exchangeRate.toCurrencyCode")
    @Mapping(target = "to.name", source = "exchangeRate.toCurrencyName")
    @Mapping(target = "to.type", ignore = true)
    @Mapping(target = "rate", source = "exchangeRate.exchangeRate")
    @Mapping(target = "lastRefreshed",
        expression = "java(DateTimeMapper.INSTANCE.mapToOffsetDateTime(api.getExchangeRate().getLastRefreshed(), api.getExchangeRate().getTimeZone()))")
    @Mapping(target = "receivedAt", expression = "java(dev.sgd.currencymate.domain.utils.DateTimeUtils.getCurrentOffsetDateTime())")
    @Mapping(target = "providerName", ignore = true)
    ExchangeRate toDomain(ExchangeRateResponse api);

}
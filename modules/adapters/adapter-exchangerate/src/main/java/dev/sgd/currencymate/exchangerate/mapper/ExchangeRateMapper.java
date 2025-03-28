package dev.sgd.currencymate.exchangerate.mapper;

import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.exchangerate.config.DefaultMapperConfig;
import dev.sgd.currencymate.exchangerate.model.ExchangeRateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(config = DefaultMapperConfig.class)
public interface ExchangeRateMapper {

    ExchangeRateMapper EXCHANGE_RATE_MAPPER = getMapper(ExchangeRateMapper.class);

    @Mapping(target = "from.code", source = "baseCode")
    @Mapping(target = "from.name", ignore = true)
    @Mapping(target = "from.type", ignore = true)
    @Mapping(target = "to.code", source = "exchangeRate.toCurrencyCode")
    @Mapping(target = "to.name", ignore = true)
    @Mapping(target = "to.type", ignore = true)
    @Mapping(target = "rate", source = "conversionRate")
    @Mapping(target = "lastRefreshed", source = "timeLastUpdateUtc")
    @Mapping(target = "receivedAt", expression = "java(dev.sgd.currencymate.domain.utils.DateTimeUtils.getCurrentOffsetDateTime())")
    @Mapping(target = "providerName", ignore = true)
    ExchangeRate toDomain(ExchangeRateResponse api);

}
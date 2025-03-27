package dev.sgd.currencymate.adapteralphavantage.mapper;

import dev.sgd.currencymate.adapteralphavantage.model.timeseries.DailyExchangeRateResponse;
import dev.sgd.currencymate.domain.model.Currency;
import dev.sgd.currencymate.domain.model.ExchangeRateDaily;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { DailyExchangeRateMapper.class, DateTimeMapper.class})
public interface DailyExchangeRateResponseMapper {

    DailyExchangeRateResponseMapper DAILY_EXCHANGE_RATE_RESPONSE_MAPPER = Mappers.getMapper(DailyExchangeRateResponseMapper.class);

    @Mapping(target = "info", expression = "java(api.getMetadata().getInformation())")
    @Mapping(target = "from.code", expression = "java(api.getMetadata().getFromSymbol())")
    @Mapping(target = "from.name", expression = "java(fromCurrency.getName())")
    @Mapping(target = "from.type", expression = "java(fromCurrency.getType())")
    @Mapping(target = "to.code", expression = "java(api.getMetadata().getToSymbol())")
    @Mapping(target = "to.name", expression = "java(toCurrency.getName())")
    @Mapping(target = "to.type", expression = "java(toCurrency.getType())")
    @Mapping(target = "lastRefreshed",
        expression = "java(DateTimeMapper.INSTANCE.mapToOffsetDateTime(api.getMetadata().getLastRefreshed(), api.getMetadata().getTimeZone()))")
    @Mapping(target = "receivedAt", expression = "java(dev.sgd.currencymate.domain.utils.DateTimeUtils.getCurrentOffsetDateTime())")
    @Mapping(target = "exchangeRateTimeSeries", source = "api.timeSeries")
    @Mapping(target = "providerName", ignore = true)
    ExchangeRateDaily toDomain(DailyExchangeRateResponse api, Currency fromCurrency, Currency toCurrency);

}
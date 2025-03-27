package dev.sgd.currencymate.adapteralphavantage.mapper;

import dev.sgd.currencymate.adapteralphavantage.model.timeseries.DailyExchangeRateResponse;
import dev.sgd.currencymate.domain.model.ExchangeRateDaily;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { DailyExchangeRateMapper.class, DateTimeMapper.class})
public interface DailyExchangeRateResponseMapper {

    DailyExchangeRateResponseMapper DAILY_EXCHANGE_RATE_RESPONSE_MAPPER = Mappers.getMapper(DailyExchangeRateResponseMapper.class);

    @Mapping(target = "info", source = "metadata.information")
    @Mapping(target = "from.code", source = "metadata.fromSymbol")
    @Mapping(target = "from.name", ignore = true)
    @Mapping(target = "from.type", ignore = true)
    @Mapping(target = "to.code", source = "metadata.toSymbol")
    @Mapping(target = "to.name", ignore = true)
    @Mapping(target = "to.type", ignore = true)
    @Mapping(target = "lastRefreshed",
        expression = "java(DateTimeMapper.INSTANCE.mapToOffsetDateTime(api.getMetadata().getLastRefreshed(), api.getMetadata().getTimeZone()))")
    @Mapping(target = "receivedAt", expression = "java(dev.sgd.currencymate.domain.utils.DateTimeUtils.getCurrentOffsetDateTime())")
    @Mapping(target = "exchangeRateTimeSeries", source = "timeSeries")
    @Mapping(target = "providerName", ignore = true)
    ExchangeRateDaily toDomain(DailyExchangeRateResponse api);

}
package dev.sgd.currencymate.alphavantage.mapper;

import dev.sgd.currencymate.alphavantage.model.daily.DailyExchangeRateResponse;
import dev.sgd.currencymate.domain.model.Currency;
import dev.sgd.currencymate.domain.model.DailyExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ExchangeRateValuesMapper.class, DateTimeMapper.class})
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
    DailyExchangeRate toDomain(DailyExchangeRateResponse api);

    default void setCurrenciesNameAndType(@MappingTarget DailyExchangeRate dailyExchangeRate,
                                          Currency fromCurrency,
                                          Currency toCurrency) {
        dailyExchangeRate.getFrom().setName(fromCurrency.getName());
        dailyExchangeRate.getFrom().setType(fromCurrency.getType());

        dailyExchangeRate.getTo().setName(toCurrency.getName());
        dailyExchangeRate.getTo().setType(toCurrency.getType());
    }

}
package dev.sgd.currencymate.adapteralphavantage.mapper;

import dev.sgd.currencymate.adapteralphavantage.model.daily.DailyExchangeRateResponse;
import dev.sgd.currencymate.adapteralphavantage.model.weekly.WeeklyExchangeRateResponse;
import dev.sgd.currencymate.domain.model.Currency;
import dev.sgd.currencymate.domain.model.DailyExchangeRate;
import dev.sgd.currencymate.domain.model.WeeklyExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ExchangeRateValuesMapper.class, DateTimeMapper.class})
public interface WeeklyExchangeRateResponseMapper {

    WeeklyExchangeRateResponseMapper WEEKLY_EXCHANGE_RATE_RESPONSE_MAPPER = Mappers.getMapper(WeeklyExchangeRateResponseMapper.class);

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
    WeeklyExchangeRate toDomain(WeeklyExchangeRateResponse api);

    default void setCurrenciesNameAndType(@MappingTarget WeeklyExchangeRate weeklyExchangeRate,
                                          Currency fromCurrency,
                                          Currency toCurrency) {
        weeklyExchangeRate.getFrom().setName(fromCurrency.getName());
        weeklyExchangeRate.getFrom().setType(fromCurrency.getType());

        weeklyExchangeRate.getTo().setName(toCurrency.getName());
        weeklyExchangeRate.getTo().setType(toCurrency.getType());
    }

}
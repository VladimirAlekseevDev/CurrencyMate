package dev.sgd.currencymate.rest.api.mapper;

import dev.sgd.currencymate.domain.model.DailyExchangeRate;
import dev.sgd.currencymate.domain.model.WeeklyExchangeRate;
import dev.sgd.currencymate.rest.api.config.DefaultMapperConfig;
import dev.sgd.currencymate.rest.api.model.response.DailyExchangeRateResponse;
import dev.sgd.currencymate.rest.api.model.response.WeeklyExchangeRateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(config = DefaultMapperConfig.class,
        uses = { CurrencyMapper.class, ExchangeRateValuesMapper.class })
public interface WeeklyExchangeRateResponseMapper {

    WeeklyExchangeRateResponseMapper WEEKLY_EXCHANGE_RATE_RESPONSE_MAPPER = getMapper(WeeklyExchangeRateResponseMapper.class);

    @Mapping(target = "exchangeRateValues", source = "exchangeRateTimeSeries")
    WeeklyExchangeRateResponse toApi(WeeklyExchangeRate domain);

}
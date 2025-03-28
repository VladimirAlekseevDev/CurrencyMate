package dev.sgd.currencymate.rest.api.mapper;

import dev.sgd.currencymate.config.DefaultMapperConfig;
import dev.sgd.currencymate.domain.model.DailyExchangeRate;
import dev.sgd.currencymate.rest.api.model.response.DailyExchangeRateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(config = DefaultMapperConfig.class,
        uses = { CurrencyMapper.class, ExchangeRateValuesMapper.class })
public interface DailyExchangeRateResponseMapper {

    DailyExchangeRateResponseMapper DAILY_EXCHANGE_RATE_RESPONSE_MAPPER = getMapper(DailyExchangeRateResponseMapper.class);

    @Mapping(target = "exchangeRateValues", source = "exchangeRateTimeSeries")
    DailyExchangeRateResponse toApi(DailyExchangeRate domain);

}
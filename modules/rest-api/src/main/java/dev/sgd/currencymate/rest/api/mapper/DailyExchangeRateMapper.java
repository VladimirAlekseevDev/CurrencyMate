package dev.sgd.currencymate.rest.api.mapper;

import dev.sgd.currencymate.domain.model.DailyExchangeRate;
import dev.sgd.currencymate.rest.api.config.DefaultMapperConfig;
import dev.sgd.currencymate.rest.api.model.response.DailyExchangeRateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = DefaultMapperConfig.class,
        uses = { CurrencyMapper.class, DailyExchangeRateValuesMapper.class })
public interface DailyExchangeRateMapper {

    DailyExchangeRateMapper DAILY_EXCHANGE_RATE_MAPPER = Mappers.getMapper(DailyExchangeRateMapper.class);

    DailyExchangeRateResponse toApi(DailyExchangeRate domain);

}
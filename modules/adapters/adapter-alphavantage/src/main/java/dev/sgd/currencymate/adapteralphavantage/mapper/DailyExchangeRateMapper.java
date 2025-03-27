package dev.sgd.currencymate.adapteralphavantage.mapper;

import dev.sgd.currencymate.adapteralphavantage.model.timeseries.DailyExchangeRateValuesDto;
import dev.sgd.currencymate.domain.model.DailyExchangeRateValues;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DailyExchangeRateMapper {

    DailyExchangeRateMapper DAILY_EXCHANGE_RATE_MAPPER = Mappers.getMapper(DailyExchangeRateMapper.class);

    DailyExchangeRateValues toDomain(DailyExchangeRateValuesDto api);

}
package dev.sgd.currencymate.adapteralphavantage.mapper;

import dev.sgd.currencymate.adapteralphavantage.model.ExchangeRateValuesDto;
import dev.sgd.currencymate.domain.model.ExchangeRateValues;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExchangeRateValuesMapper {

    ExchangeRateValuesMapper EXCHANGE_RATE_VALUES_MAPPER = Mappers.getMapper(ExchangeRateValuesMapper.class);

    ExchangeRateValues toDomain(ExchangeRateValuesDto api);

}
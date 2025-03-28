package dev.sgd.currencymate.alphavantage.mapper;

import dev.sgd.currencymate.alphavantage.config.DefaultMapperConfig;
import dev.sgd.currencymate.alphavantage.model.ExchangeRateValuesDto;
import dev.sgd.currencymate.domain.model.ExchangeRateValues;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapperConfig.class)
public interface ExchangeRateValuesMapper {

    ExchangeRateValues toDomain(ExchangeRateValuesDto api);

}
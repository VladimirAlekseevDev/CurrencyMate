package dev.sgd.currencymate.rest.api.mapper;

import dev.sgd.currencymate.config.DefaultMapperConfig;
import dev.sgd.currencymate.domain.model.ExchangeRateValues;
import dev.sgd.currencymate.rest.api.model.ExchangeRateValuesDto;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapperConfig.class)
public interface ExchangeRateValuesMapper {

    ExchangeRateValuesDto toApi(ExchangeRateValues domain);

}
package dev.sgd.currencymate.rest.api.mapper;

import dev.sgd.currencymate.domain.model.DailyExchangeRateValues;
import dev.sgd.currencymate.rest.api.config.DefaultMapperConfig;
import dev.sgd.currencymate.rest.api.model.DailyExchangeRateValuesDto;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapperConfig.class)
public interface DailyExchangeRateValuesMapper {

    DailyExchangeRateValuesDto toApi(DailyExchangeRateValues domain);

}
package dev.sgd.currencymate.rest.api.mapper;

import dev.sgd.currencymate.domain.model.ExchangeRate;
import dev.sgd.currencymate.rest.api.config.DefaultMapperConfig;
import dev.sgd.currencymate.rest.api.model.response.ExchangeRateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = DefaultMapperConfig.class,
        uses = { CurrencyMapper.class })
public interface ExchangeRateMapper {

    ExchangeRateMapper EXCHANGE_RATE_MAPPER = Mappers.getMapper(ExchangeRateMapper.class);

    ExchangeRateResponse toApi(ExchangeRate domain);

}

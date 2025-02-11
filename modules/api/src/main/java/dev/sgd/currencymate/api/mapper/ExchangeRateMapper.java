package dev.sgd.currencymate.api.mapper;

import dev.sgd.currencymate.api.model.ExchangeRateResponse;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { CurrencyMapper.class })
public interface ExchangeRateMapper {

    ExchangeRateMapper INSTANCE = Mappers.getMapper(ExchangeRateMapper.class);

    ExchangeRateResponse toApi(ExchangeRate domain);

}

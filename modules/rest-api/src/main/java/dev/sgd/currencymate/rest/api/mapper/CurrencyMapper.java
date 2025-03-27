package dev.sgd.currencymate.rest.api.mapper;

import dev.sgd.currencymate.rest.api.model.CurrencyDto;
import dev.sgd.currencymate.domain.model.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrencyMapper {

    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    CurrencyDto toApi(Currency domain);

}
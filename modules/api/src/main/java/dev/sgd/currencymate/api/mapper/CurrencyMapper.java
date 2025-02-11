package dev.sgd.currencymate.api.mapper;

import dev.sgd.currencymate.api.model.CurrencyDto;
import dev.sgd.currencymate.domain.model.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrencyMapper {

    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    CurrencyDto toApi(Currency domain);

}
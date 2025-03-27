package dev.sgd.currencymate.rest.api.mapper;

import dev.sgd.currencymate.rest.api.config.DefaultMapperConfig;
import dev.sgd.currencymate.rest.api.model.CurrencyDto;
import dev.sgd.currencymate.domain.model.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = DefaultMapperConfig.class)
public interface CurrencyMapper {

    CurrencyDto toApi(Currency domain);

}
package dev.sgd.currencymate.rest.api.mapper;

import dev.sgd.currencymate.config.DefaultMapperConfig;
import dev.sgd.currencymate.domain.model.Currency;
import dev.sgd.currencymate.rest.api.model.CurrencyDto;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapperConfig.class)
public interface CurrencyMapper {

    CurrencyDto toApi(Currency domain);

}
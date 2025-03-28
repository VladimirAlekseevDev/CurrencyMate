package dev.sgd.currencymate.exchangerate.mapper;

import dev.sgd.currencymate.exchangerate.config.DefaultMapperConfig;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(config = DefaultMapperConfig.class)
public interface CurrencyMapper {

    CurrencyMapper CURRENCY_MAPPER = getMapper(CurrencyMapper.class);

}
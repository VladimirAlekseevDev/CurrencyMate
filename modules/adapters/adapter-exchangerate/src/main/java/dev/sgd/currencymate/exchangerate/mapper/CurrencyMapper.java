package dev.sgd.currencymate.exchangerate.mapper;

import dev.sgd.currencymate.domain.model.Currency;
import dev.sgd.currencymate.exchangerate.config.DefaultMapperConfig;
import dev.sgd.currencymate.exchangerate.model.CurrencyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(config = DefaultMapperConfig.class)
public interface CurrencyMapper {

    CurrencyMapper CURRENCY_MAPPER = getMapper(CurrencyMapper.class);

    @Mapping(target = "type", ignore = true, defaultExpression = "java(CurrencyType.FIAT)")
    Currency toDomain(CurrencyDto api);

    List<Currency> toDomain(List<CurrencyDto> api);

}
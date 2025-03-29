package dev.sgd.currencymate.exchangerate.mapper;

import dev.sgd.currencymate.config.DefaultMapperConfig;
import dev.sgd.currencymate.domain.model.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(config = DefaultMapperConfig.class)
public interface CurrencyMapper {

    CurrencyMapper CURRENCY_MAPPER = getMapper(CurrencyMapper.class);

    @Mapping(target = "type", ignore = true, defaultExpression = "java(CurrencyType.FIAT)")
    default Currency toDomain(List<String> api) {
        Currency currency = new Currency();
        currency.setCode(api.get(0));
        currency.setName(api.get(1));
        return currency;
    }

    List<Currency> toDomains(List<List<String>> api);

}
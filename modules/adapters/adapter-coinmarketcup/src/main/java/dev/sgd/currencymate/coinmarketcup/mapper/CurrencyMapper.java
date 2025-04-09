package dev.sgd.currencymate.coinmarketcup.mapper;

import dev.sgd.currencymate.coinmarketcup.model.currency.CurrencyInfo;
import dev.sgd.currencymate.coinmarketcup.model.currency.CurrencyInfoDto;
import dev.sgd.currencymate.config.DefaultMapperConfig;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(config = DefaultMapperConfig.class)
public interface CurrencyMapper {

    CurrencyMapper CURRENCY_MAPPER = getMapper(CurrencyMapper.class);

    @IterableMapping(qualifiedByName = "toDomainCrypto")
    List<CurrencyInfo> toDomainCrypto(List<CurrencyInfoDto> dtos);

    @Named("toDomainCrypto")
    @Mapping(target = "code", source = "symbol")
    @Mapping(target = "type", expression = "java(dev.sgd.currencymate.domain.enums.CurrencyType.CRYPTO)")
    CurrencyInfo toDomainCrypto(CurrencyInfoDto dto);

    @IterableMapping(qualifiedByName = "toDomainFiat")
    List<CurrencyInfo> toDomainFiat(List<CurrencyInfoDto> dtos);

    @Named("toDomainFiat")
    @Mapping(target = "code", source = "symbol")
    @Mapping(target = "type", expression = "java(dev.sgd.currencymate.domain.enums.CurrencyType.FIAT)")
    CurrencyInfo toDomainFiat(CurrencyInfoDto dto);
}
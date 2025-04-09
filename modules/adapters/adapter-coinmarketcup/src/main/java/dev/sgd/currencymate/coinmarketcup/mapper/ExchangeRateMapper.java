package dev.sgd.currencymate.coinmarketcup.mapper;

import dev.sgd.currencymate.coinmarketcup.model.ExchangeRateValueDto;
import dev.sgd.currencymate.coinmarketcup.model.currency.CurrencyInfo;
import dev.sgd.currencymate.config.DefaultMapperConfig;
import dev.sgd.currencymate.domain.model.Currency;
import dev.sgd.currencymate.domain.model.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(config = DefaultMapperConfig.class)
public interface ExchangeRateMapper {

    ExchangeRateMapper EXCHANGE_RATE_MAPPER = getMapper(ExchangeRateMapper.class);

    @Mapping(target = "from", ignore = true)
    @Mapping(target = "to", ignore = true)
    @Mapping(target = "providerName", ignore = true)
    @Mapping(target = "rate", source = "price")
    @Mapping(target = "lastRefreshed", source = "lastUpdated")
    @Mapping(target = "receivedAt", expression = "java(dev.sgd.currencymate.domain.utils.DateTimeUtils.getCurrentOffsetDateTime())")
    ExchangeRate toDomain(ExchangeRateValueDto api);

    default void setCurrenciesNameAndType(@MappingTarget ExchangeRate exchangeRate,
                                          CurrencyInfo fromCurrency,
                                          CurrencyInfo toCurrency) {
        Currency from = new Currency();
        from.setCode(fromCurrency.getCode());
        from.setName(fromCurrency.getName());
        from.setType(fromCurrency.getType());
        exchangeRate.setFrom(from);

        Currency to = new Currency();
        to.setCode(toCurrency.getCode());
        to.setName(toCurrency.getName());
        to.setType(toCurrency.getType());
        exchangeRate.setTo(to);
    }
}
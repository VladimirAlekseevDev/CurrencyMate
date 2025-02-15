package dev.sgd.currencymate.adapteralphavantage.mapper;

import dev.sgd.currencymate.adapteralphavantage.model.timeseries.TimeSeriesExchangeRateDto;
import dev.sgd.currencymate.domain.model.TimeSeriesExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TimeSeriesExchangeRateMapper {

    TimeSeriesExchangeRateMapper INSTANCE = Mappers.getMapper(TimeSeriesExchangeRateMapper.class);

    TimeSeriesExchangeRate toDomain(TimeSeriesExchangeRateDto api);

}
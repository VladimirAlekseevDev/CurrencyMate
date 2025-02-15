package dev.sgd.currencymate.adapteralphavantage.mapper;

import dev.sgd.currencymate.adapteralphavantage.model.timeseries.TimeSeriesDailyResponse;
import dev.sgd.currencymate.domain.model.TimeSeries;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { TimeSeriesExchangeRateMapper.class, DateTimeMapper.class})
public interface TimeSeriesMapper {

    TimeSeriesMapper INSTANCE = Mappers.getMapper(TimeSeriesMapper.class);

    @Mapping(target = "info", source = "metadata.information")
    @Mapping(target = "from.code", source = "metadata.fromSymbol")
    @Mapping(target = "from.name", ignore = true)
    @Mapping(target = "to.code", source = "metadata.toSymbol")
    @Mapping(target = "to.name", ignore = true)
    @Mapping(target = "lastRefreshed",
        expression = "java(DateTimeMapper.INSTANCE.mapToOffsetDateTime(api.getMetadata().getLastRefreshed(), api.getMetadata().getTimeZone()))")
    @Mapping(target = "receivedAt", ignore = true)
    @Mapping(target = "exchangeRateTimeSeries", source = "timeSeries")
    TimeSeries toDomain(TimeSeriesDailyResponse api);

}
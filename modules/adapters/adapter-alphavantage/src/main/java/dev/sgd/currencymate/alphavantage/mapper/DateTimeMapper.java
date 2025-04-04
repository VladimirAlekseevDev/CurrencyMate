package dev.sgd.currencymate.alphavantage.mapper;

import dev.sgd.currencymate.config.DefaultMapperConfig;
import dev.sgd.currencymate.domain.utils.DateTimeUtils;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(config = DefaultMapperConfig.class)
public interface DateTimeMapper {

    DateTimeMapper INSTANCE = getMapper(DateTimeMapper.class);

    default OffsetDateTime mapToOffsetDateTime(LocalDate lastRefreshed, String timeZone) {
        if (lastRefreshed == null) {
            return null;
        }

        return DateTimeUtils.toOffsetDateTime(lastRefreshed.atStartOfDay(), timeZone);
    }

    default OffsetDateTime mapToOffsetDateTime(LocalDateTime lastRefreshed, String timeZone) {
        if (lastRefreshed == null) {
            return null;
        }

        return DateTimeUtils.toOffsetDateTime(lastRefreshed, timeZone);
    }

}
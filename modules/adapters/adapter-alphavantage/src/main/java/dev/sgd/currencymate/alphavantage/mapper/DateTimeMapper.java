package dev.sgd.currencymate.alphavantage.mapper;

import dev.sgd.currencymate.domain.utils.DateTimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Mapper
public interface DateTimeMapper {

    DateTimeMapper INSTANCE = Mappers.getMapper(DateTimeMapper.class);

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
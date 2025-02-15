package dev.sgd.currencymate.adapteralphavantage.mapper;

import dev.sgd.currencymate.domain.utils.DateTimeUtils;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DateTimeMapper {

    DateTimeMapper INSTANCE = Mappers.getMapper(DateTimeMapper.class);

    default OffsetDateTime mapToOffsetDateTime(LocalDateTime lastRefreshed, String timeZone) {
        if (lastRefreshed == null) {
            return null;
        }

        return DateTimeUtils.toOffsetDateTime(lastRefreshed, timeZone);
    }

}
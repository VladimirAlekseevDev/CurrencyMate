package dev.sgd.currencymate.domain.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class DateTimeUtils {

    private static final ZoneId DEFAULT_TIME_ZONE = ZoneId.systemDefault();

    public static OffsetDateTime getCurrentOffsetDateTime() {
        return OffsetDateTime.now(DEFAULT_TIME_ZONE).truncatedTo(ChronoUnit.SECONDS);
    }

    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now(DEFAULT_TIME_ZONE);
    }

    public static ZonedDateTime getCurrentZonedDateTime() {
        return ZonedDateTime.now(DEFAULT_TIME_ZONE);
    }

    public static OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(DEFAULT_TIME_ZONE).toOffsetDateTime();
    }

    public static OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime, String timeZone) {
        return localDateTime.atZone(ZoneId.of(timeZone)).toOffsetDateTime();
    }


}
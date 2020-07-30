package br.com.dbccompany.core.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public abstract class DateUtils {

    public static LocalDateTime toLocalDateTime(final Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Long getDifferenceMilliseconds(final Date date, final Date dateToCompare) {
        return date.getTime() - dateToCompare.getTime();
    }

    public static Date toDate(final LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }
}

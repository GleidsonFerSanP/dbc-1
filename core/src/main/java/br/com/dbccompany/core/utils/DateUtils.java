package br.com.dbccompany.core.utils;

import lombok.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static br.com.dbccompany.core.utils.TimeMachine.now;
import static br.com.dbccompany.core.utils.TimeMachine.useFixedClockAt;

public abstract class DateUtils {

    public static LocalDateTime toLocalDateTime(final Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Date toDate(final LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

    public static Date textToDate(@NonNull final String dateString, final String pattern) throws ParseException {
        final DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(dateString);
    }

    public static void mockCalendar(final Integer dayOfMonth, final Integer hour) {
        final LocalDateTime now = now();
        final LocalDateTime customDate = LocalDateTime
                .of(now.getYear(), now.getMonthValue(), dayOfMonth, hour, 0);
        useFixedClockAt(customDate);
    }
    public static void mockCalendar(final Integer year, final Integer month, final Integer dayOfMonth, final Integer hour) {
        final LocalDateTime customDate = LocalDateTime
                .of(year, month, dayOfMonth, hour, 0);
        useFixedClockAt(customDate);
    }
}

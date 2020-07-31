package br.com.dbccompany.core.utils;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

public abstract class TimeMachine {
    private static Clock clock = Clock.systemDefaultZone();
    private static ZoneId zoneId = ZoneId.systemDefault();
    public static LocalDateTime now() {
        return LocalDateTime.now(getClock());
    }
    public static Calendar nowCalendar() {
        final LocalDateTime localDateTime = LocalDateTime.now(getClock());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.toDate(localDateTime));
        return calendar;
    }
    public static LocalDate localDateNow() {
        return LocalDate.now(getClock());
    }
    public static void useFixedClockAt(final LocalDateTime date){
        clock = Clock.fixed(date.atZone(zoneId).toInstant(), zoneId);
    }
    public static void useSystemDefaultZoneClock(){
        clock = Clock.systemDefaultZone();
    }
    private static Clock getClock() {
        return clock ;
    }
}
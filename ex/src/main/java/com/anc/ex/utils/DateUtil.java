package com.anc.ex.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * Date Utils
 *
 * @author Alan
 *
 * Include Before Java 8 Class<br/>
 * java.util.Date<br/>
 * java.util.Calendar<br/>
 * <br/>
 * Include After Java 8 Class<br/>
 * java.time.LocalDate - 包含目前所在時區資訊的日期<br/>
 * java.time.LocalTime - 包含目前所在時區資訊的時間<br/>
 * java.time.LocalDateTime - 包含目前所在時區資訊的日期時間<br/>
 * java.time.ZonedDate - 包含指定時區資訊的日期<br/>
 * java.time.ZoneDateTime - 包含指定時區資訊的日期時間<br/>
 * java.time.Instant - UTC日期時間(自1970-01-01T00:00:00.000Z以來的毫秒數)<br/>
 * java.time.Duration - 小時以下的連續時間工具<br/>
 * java.time.Period - 以天週月年為單位的連續時間工具<br/>
 * java.time.format.DateTimeFormatter - 格式化工具
 */
public class DateUtil {

    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Custom local date time local date time.
     *
     * @param year  the year
     * @param month the month
     * @param day   the day
     * @param hour  the hour
     * @param min   the min
     * @param sec   the sec
     * @param ns    the ns
     * @return the local date time
     */
    public LocalDateTime customLocalDateTime(int year, int month, int day, int hour, int min, int sec, int ns) {
        return LocalDateTime.of(year, month, day, hour, min, sec, ns);
    }

    /**
     * Custom local date local date.
     *
     * @param year  the year
     * @param month the month
     * @param day   the day
     * @return the local date
     */
    public LocalDate customLocalDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    /**
     * Custom local time local time.
     *
     * @param hour the hour
     * @param min  the min
     * @param sec  the sec
     * @param ns   the ns
     * @return the local time
     */
    public LocalTime customLocalTime(int hour, int min, int sec, int ns) {
        return LocalTime.of(hour, min, sec, ns);
    }


    /**
     * Gets zone id set.
     *
     * @return the zone id set
     */
    public Set<String> getZoneIdSet() {
        return ZoneId.getAvailableZoneIds();
    }

    /**
     * Gets local zone id.
     *
     * @return the local zone id
     */
    public ZoneId getLocalZoneId() {
        return ZoneId.systemDefault();
    }

    /**
     * Local date to date date.
     *
     * @param localDate the local date
     * @return the date
     */
    public Date localDateToDate(LocalDate localDate) {
        ZonedDateTime zdt = localDate.atStartOfDay(getLocalZoneId());
        Instant instant = zdt.toInstant();
        return Date.from(instant);
    }

    /**
     * Local date time to date date.
     *
     * @param localDateTime the local date time
     * @return the date
     */
    public Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZonedDateTime zdt = localDateTime.atZone(getLocalZoneId());
        Instant instant = zdt.toInstant();
        return Date.from(instant);
    }

    /**
     * Date to local date local date.
     *
     * @param date the date
     * @return the local date
     */
    public LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(getLocalZoneId());
        return zdt.toLocalDate();
    }

    /**
     * Date to local time local time.
     *
     * @param date the date
     * @return the local time
     */
    public LocalTime dateToLocalTime(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(getLocalZoneId());
        return zdt.toLocalTime();
    }

    /**
     * Date to local date time local date time.
     *
     * @param date the date
     * @return the local date time
     */
    public LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(getLocalZoneId());
        return zdt.toLocalDateTime();
    }

    /**
     * Gets today less time.
     *
     * @return the today less time
     */
    public LocalDate getTodayLessTime() {
        return LocalDate.now();
    }

    /**
     * Gets today and time.
     *
     * @return the today and time
     */
    public LocalDateTime getTodayAndTime() {
        return LocalDateTime.now();
    }

    /**
     * Merge date and time local date time.
     *
     * @param localDate the local date
     * @param localTime the local time
     * @return the local date time
     */
    public LocalDateTime mergeDateAndTime(LocalDate localDate, LocalTime localTime) {
        return localDate.atTime(localTime);
    }

    /**
     * Grep local date local date.
     *
     * @param localDateTime the local date time
     * @return the local date
     */
    public LocalDate grepLocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();
    }

    /**
     * Grep local time local time.
     *
     * @param localDateTime the local date time
     * @return the local time
     */
    public LocalTime grepLocalTime(LocalDateTime localDateTime) {
        return localDateTime.toLocalTime();
    }

    /**
     * Gets first day of month.
     *
     * @param localDate the local date
     * @return the first day of month
     */
    public LocalDate getFirstDayOfMonth(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * Gets last day of month.
     *
     * @param localDate the local date
     * @return the last day of month
     */
    public LocalDate getLastDayOfMonth(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * Gets first pointer in month.
     *
     * @param localDate the local date
     * @param day       the day
     * @return the first pointer in month
     */
    public LocalDate getFirstPointerInMonth(LocalDate localDate, DayOfWeek day) {
        return localDate.with(TemporalAdjusters.firstInMonth(day)); // 2015-01-05
    }

    /**
     * Gets last pointer in month.
     *
     * @param localDate the local date
     * @param day       the day
     * @return the last pointer in month
     */
    public LocalDate getLastPointerInMonth(LocalDate localDate, DayOfWeek day) {
        return localDate.with(TemporalAdjusters.lastInMonth(day));
    }

    /**
     * Custom date time formatter date time formatter.
     *
     * @param format the format
     * @return the date time formatter
     */
    public DateTimeFormatter customDateTimeFormatter(String format) {
        return DateTimeFormatter.ofPattern(format);
    }

    /**
     * Parse str to local date time local date time.
     *
     * @param str    the str
     * @param format the format
     * @return the local date time
     */
    public LocalDateTime parseStrToLocalDateTime(String str, String format) {
        return LocalDateTime.parse(str, customDateTimeFormatter(format));
    }

    /**
     * Parse local date time to str string.
     *
     * @param localDateTime the local date time
     * @param format        the format
     * @return the string
     */
    public String parseLocalDateTimeToStr(LocalDateTime localDateTime, String format) {
        return localDateTime.format(customDateTimeFormatter(format));
    }

    /**
     * Year diff integer.
     *
     * @param localDateA the local date a
     * @param localDateB the local date b
     * @return the integer
     */
    public Integer yearDiff(LocalDate localDateA, LocalDate localDateB) {
        Period period = Period.between(localDateA, localDateB);
        return period.getYears();
    }

    /**
     * Month diff integer.
     *
     * @param localDateA the local date a
     * @param localDateB the local date b
     * @return the integer
     */
    public Integer monthDiff(LocalDate localDateA, LocalDate localDateB) {
        Period period = Period.between(localDateA, localDateB);
        return period.getMonths();
    }

    /**
     * Day diff integer.
     *
     * @param localDateA the local date a
     * @param localDateB the local date b
     * @return the integer
     */
    public Integer dayDiff(LocalDate localDateA, LocalDate localDateB) {
        Period period = Period.between(localDateA, localDateB);
        return period.getDays();
    }

    /**
     * Add day local date.
     *
     * @param localDate the local date
     * @param addDays   the add days
     * @return the local date
     */
    public LocalDate addDay(LocalDate localDate, int addDays) {
        if (addDays == 0) return localDate;
        if (addDays < 0) return localDate.minusDays(addDays);
        return localDate.plusDays(addDays);
    }


    /**
     * Set time 00:00:00.000.
     *
     * @param srcDate the src date
     * @return the date
     */
    public static Date truncDateTime(Date srcDate) {
        return customDateTime(srcDate, -1, -1, -1, 0, 0, 0, 0);
    }

    /**
     * Set time 23:59:59.999.
     *
     * @param srcDate the src date
     * @return the date
     */
    public static Date truncEndDateTime(Date srcDate) {
        return customDateTime(srcDate, -1, -1, -1, 23, 59, 59, 999);
    }

    /**
     * Custom date time date.
     *
     * @param srcDate the src date
     * @param year    the year
     * @param month   the month
     * @param day     the day
     * @param hour    the hour
     * @param minute  the minute
     * @param second  the second
     * @param ms      the ms
     * @return the date
     */
    public static Date customDateTime(Date srcDate, int year, int month, int day, int hour, int minute, int second, int ms) {
        Calendar cal = Calendar.getInstance();
        Date resultDate = null;
        if (srcDate != null) {
            cal.setTime(srcDate);
            if (year < 0) cal.set(Calendar.YEAR, year);
            if (month < 0) cal.set(Calendar.MONTH, month);
            if (day < 0) cal.set(Calendar.DAY_OF_MONTH, day);
            if (hour < 0) cal.set(Calendar.HOUR_OF_DAY, hour);
            if (minute < 0) cal.set(Calendar.MINUTE, minute);
            if (second < 0) cal.set(Calendar.SECOND, second);
            if (ms < 0) cal.set(Calendar.MILLISECOND, ms);
            resultDate = cal.getTime();
        }
        return resultDate;
    }

    /**
     * Day diff integer.
     *
     * @param dt1 the dt 1
     * @param dt2 the dt 2
     * @return the integer
     */
    public static Integer dayDiff(Date dt1, Date dt2) {
        return msDiff(dt1, dt2).intValue() + 1;
    }

    /**
     * Ms diff long.
     *
     * @param dt1 the dt 1
     * @param dt2 the dt 2
     * @return the long
     */
    public static Long msDiff(Date dt1, Date dt2) {
        return (truncDateTime(dt1).getTime() - truncDateTime(dt2).getTime()) / (1000 * 60 * 60 * 24);
    }

    /**
     * Add day date.
     *
     * @param d      the d
     * @param addDay the add day
     * @return the date
     */
    public static Date addDay(Date d, int addDay) {
        return addDateTime(d, 0, 0, addDay, 0, 0, 0).getTime();
    }

    /**
     * Add month date.
     *
     * @param d        the d
     * @param addMonth the add month
     * @return the date
     */
    public static Date addMonth(Date d, int addMonth) {
        return addDateTime(d, 0, addMonth, 0, 0, 0, 0).getTime();
    }

    /**
     * Add date time calendar.
     *
     * @param date   the date
     * @param year   the year
     * @param month  the month
     * @param day    the day
     * @param hour   the hour
     * @param minute the minute
     * @param second the second
     * @return the calendar
     */
    public static Calendar addDateTime(Date date, int year, int month, int day, int hour, int minute,
                                       int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (year != 0) cal.add(Calendar.YEAR, year);
        if (month != 0) cal.add(Calendar.MONTH, month);
        if (day != 0) cal.add(Calendar.DAY_OF_MONTH, day);
        if (hour != 0) cal.add(Calendar.HOUR_OF_DAY, hour);
        if (minute != 0) cal.add(Calendar.MINUTE, minute);
        if (second != 0) cal.add(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    /**
     * Gets cal instance.
     *
     * @param date the date
     * @return the cal instance
     */
    public static Calendar getCalInstance(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * Gets year.
     *
     * @param date the date
     * @return the year
     */
    public static Integer getYear(Date date) {
        return getCalInstance(date).get(Calendar.YEAR);
    }

    /**
     * Gets month.
     *
     * @param date the date
     * @return the month
     */
    public static Integer getMonth(Date date) {
        return getCalInstance(date).get(Calendar.MONTH);
    }

    /**
     * Gets day.
     *
     * @param date the date
     * @return the day
     */
    public static Integer getDay(Date date) {
        return getCalInstance(date).get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Gets hour.
     *
     * @param date the date
     * @return the hour
     */
    public static Integer getHour(Date date) {
        return getCalInstance(date).get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Gets minute.
     *
     * @param date the date
     * @return the minute
     */
    public static Integer getMinute(Date date) {
        return getCalInstance(date).get(Calendar.MINUTE);
    }

    /**
     * Gets second.
     *
     * @param date the date
     * @return the second
     */
    public static Integer getSecond(Date date) {
        return getCalInstance(date).get(Calendar.SECOND);
    }

    /**
     * Gets milli second.
     *
     * @param date the date
     * @return the milli second
     */
    public static Integer getMilliSecond(Date date) {
        return getCalInstance(date).get(Calendar.MILLISECOND);
    }

    /**
     * Conv str to date date.
     *
     * @param str    the str
     * @param format the format
     * @return the date
     */
    public static Date convStrToDate(String str, String format) {
        if (str == null || str.trim().isEmpty() || format == null || format.trim().isEmpty()) {
            return null;
        }
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException se) {
            return null;
        }
    }

    /**
     * Conv date to str string.
     *
     * @param date   the date
     * @param format the format
     * @return the string
     */
    public static String convDateToStr(Date date, String format) {
        if (date == null || format.trim().isEmpty()) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * Conv date to hhmm string.
     *
     * @param date the date
     * @return the string
     */
    public static String convDateToHHMM(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat("HH:mm").format(date);
        } catch (Exception e) {
            return null;
        }
    }

}

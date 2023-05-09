package ir.deltasink.feagen.common.utils;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.text.ParsePosition;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

/**
 * This class provides utility methods for working with date and time values.
 * It uses the ZonedDateTime class to represent date and time with a time zone.
 * It also provides methods for formatting and parsing date and time strings.
 * @see ZonedDateTime
 * @see DateTimeFormatter
 * @see ZoneId
 */
@Slf4j
public class DateTime {

    /**
     * Returns the current date and time with a given time zone.
     * @param zoneId the ID of the time zone to use
     * @return a ZonedDateTime instance with the current date and time and the given time zone
     */
    public static ZonedDateTime now(String zoneId){
        return ZonedDateTime.now().withZoneSameInstant(ZoneId.of(zoneId));
    }

    /**
     * Returns the date and time of the last day of the month of a given date and time.
     * The time and time zone are preserved from the input value.
     * @param dt the date and time to adjust
     * @return a ZonedDateTime instance with the date and time of the last day of the month
     */
    public static ZonedDateTime lastDayOfMonth(ZonedDateTime dt){
        return dt.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * Returns the date and time of the first day of the month of a given date and time.
     * The time and time zone are preserved from the input value.
     * @param dt the date and time to adjust
     * @return a ZonedDateTime instance with the date and time of the first day of the month
     */
    public static ZonedDateTime firstDayOfMonth(ZonedDateTime dt){
        return dt.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * Formats a given date and time using a default format of "yyyy-MM-dd'T'HH:mm:ss".
     * The method uses the DateTimeFormatter class to format the ZonedDateTime instance to a string.
     * @param dt the date and time to format
     * @return a string representing the formatted date and time
     */
    public static String format(ZonedDateTime dt){
        return format(dt, "yyyy-MM-dd'T'HH:mm:ss");
    }

    /**
     * Formats a given date and time using input format.
     * The method uses the DateTimeFormatter class to format the ZonedDateTime instance to a string.
     * @param dt the date and time to format
     * @param format the format of date and time
     * @return a string representing the formatted date and time
     */
    public static String format(ZonedDateTime dt, String format){
        return dt.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Parses a given date and time string and returns a ZonedDateTime instance with a given time zone.
     * The method tries to parse the string using three different formats: ISO_ZONED_DATE_TIME, ISO_LOCAL_DATE_TIME, and ISO_LOCAL_DATE.
     * If none of the formats match, the method returns null.
     * @param dt the date and time string to parse
     * @param zoneId the ID of the time zone to use
     * @return a ZonedDateTime instance with the parsed date and time and the given time zone, or null if the string cannot be parsed
     */
    public static ZonedDateTime parse(String dt, String zoneId){
        dt = dt.trim();
        val zone = ZoneId.of(zoneId);
        if (checkIfInFormat(dt, DateTimeFormatter.ISO_ZONED_DATE_TIME))
            return ZonedDateTime.parse(dt).withZoneSameInstant(zone);

        if (checkIfInFormat(dt, DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            return ZonedDateTime.of(LocalDateTime.parse(dt), zone);

        if (checkIfInFormat(dt, DateTimeFormatter.ISO_LOCAL_DATE))
            return ZonedDateTime.of(LocalDate.parse(dt), LocalTime.of(0, 0, 0), zone);

        log.warn("Input `{}` is not valid for DateTime format. Return null as default value.", dt);
        return null;
    }

    /**
     * Checks if a given date and time string is in a given format.
     * The format is specified by a DateTimeFormatter instance.
     * @param dt the date and time string to check
     * @param formatter the DateTimeFormatter instance to use
     * @return true if the date and time string is in the given format, false otherwise
     */
    public static boolean checkIfInFormat(String dt, DateTimeFormatter formatter){
        val position = new ParsePosition(0);
        TemporalAccessor accessor = formatter.parseUnresolved(dt, new ParsePosition(0));
        return accessor != null && position.getErrorIndex() < 0;
    }

    /**
     * Converts a given date and time string to a timestamp in milliseconds.
     * The method parses the string using the parse method and then converts the ZonedDateTime instance to epoch seconds and multiplies by 1000.
     * @param datetime the date and time string to convert
     * @param zoneId the ID of the time zone to use
     * @return a long value representing the timestamp in milliseconds
     */
    public static long convertDatetimeToTimestamp(String datetime, String zoneId) {
        ZonedDateTime zonedDateTime = DateTime.parse(datetime, zoneId);
        return Objects.requireNonNull(zonedDateTime).toEpochSecond() * 1000;
    }
}

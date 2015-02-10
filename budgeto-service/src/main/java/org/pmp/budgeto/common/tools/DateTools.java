package org.pmp.budgeto.common.tools;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * helper for date
 */
public class DateTools {

    public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormat.forPattern("yyyy-MM-dd");

    public static final String PATTERN_DATETIME_WITHZONE = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static final DateTimeFormatter FORMATTER_DATETIME_WITHZONE = DateTimeFormat.forPattern(PATTERN_DATETIME_WITHZONE);

    /**
     * se to private
     */
    private DateTools() {
        super();
    }

    /**
     * convert datetime to UTC truncating time
     *
     * @param date the date to modify
     * @return the new date
     */
    public static DateTime toUTCDate(DateTime date) {
        if (date == null) {
            return date;
        }
        return date.withZoneRetainFields(DateTimeZone.UTC)
            .withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    }

}

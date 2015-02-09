package org.pmp.budgeto.common.tools;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * helper for date
 */
public class DateTools {

    public static final DateTimeFormatter FORMATTER_DATETIME_WITHZONE = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss ZZZ");

    /**
     * se to private
     */
    private DateTools() {
        super();
    }

    /**
     * convert date to UTC keeping data same as the original timezone
     *
     * @param date the date to modify
     * @return the new date
     */
    public static DateTime toUTC(DateTime date) {
        if (date == null) {
            return date;
        }
        return date.withZoneRetainFields(DateTimeZone.UTC);
    }

    /**
     * truncate hour of a date
     *
     * @param date the date to truncate
     * @return the date truncated
     */
    public static DateTime truncateTime(DateTime date) {
        if (date == null) {
            return date;
        }
        return date.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    }

}

package org.pmp.budgeto.common.tools;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 * helper for date
 */
@Component
public class DateToolsImpl implements DateTools {

    public String getPatternDatetimeWithzone() {
        return "yyyy-MM-dd'T'HH:mm:ssZ";
    }

    public DateTimeFormatter getFormatterDate() {
        return DateTimeFormat.forPattern("yyyy-MM-dd");
    }

    public DateTimeFormatter getFormatterDatetimeWithzone() {
        return DateTimeFormat.forPattern(getPatternDatetimeWithzone());
    }

    public DateTime toUTCDate(DateTime date) {
        if (date == null) {
            return date;
        }
        return date.withZoneRetainFields(DateTimeZone.UTC)
                .withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    }

}

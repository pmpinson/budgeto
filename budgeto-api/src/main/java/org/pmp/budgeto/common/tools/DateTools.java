package org.pmp.budgeto.common.tools;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

/**
 * helper for date
 */
public interface DateTools {

    /**
     * get the pattern to formate datetime with zone
     *
     * @return the pattern
     */
    String getPatternDatetimeWithzone();

    /**
     * get the formatter to formate date without zone
     *
     * @return the formatter
     */
    DateTimeFormatter getFormatterDate();

    /**
     * get the formatter to formate datetime with zone
     *
     * @return the formatter
     */
    DateTimeFormatter getFormatterDatetimeWithzone();

    /**
     * convert datetime to UTC truncating time
     *
     * @param date the date to modify
     * @return the new date
     */
    DateTime toUTCDate(DateTime date);

}

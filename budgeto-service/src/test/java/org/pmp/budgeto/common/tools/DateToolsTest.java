package org.pmp.budgeto.common.tools;

import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;


public class DateToolsTest {

    @Test
    public void toUTCWithOtherTimeZone() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss.SSS ZZZ");
        DateTime dateOneWithTimeZone = formatter.parseDateTime("2014/02/26 18:52:26.626 America/Toronto");

        DateTime dateOneWithGMT = formatter.parseDateTime("2014/02/26 18:52:26.626 UTC");
        Assertions.assertThat(DateTools.toUTC(dateOneWithTimeZone)).isEqualTo(dateOneWithGMT);
    }

    @Test
    public void toUTCWithGMTTimeZone() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss.SSS ZZZ");
        DateTime dateOneWithTimeZone = formatter.parseDateTime("2014/02/26 18:52:26.626 UTC");

        DateTime dateOneWithGMT = formatter.parseDateTime("2014/02/26 18:52:26.626 UTC");
        Assertions.assertThat(DateTools.toUTC(dateOneWithTimeZone)).isEqualTo(dateOneWithGMT);
    }

    @Test
    public void truncateTimeWithOtherTimeZone() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss.SSS ZZZ");
        DateTime dateOneWithTimeZone = formatter.parseDateTime("2014/02/26 18:52:26.626 America/Toronto");

        DateTime dateOneWithGMT = formatter.parseDateTime("2014/02/26 00:00:00.000 America/Toronto");
        Assertions.assertThat(DateTools.truncateTime(dateOneWithTimeZone)).isEqualTo(dateOneWithGMT);
    }

    @Test
    public void truncateTimeWithGMTTimeZone() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss.SSS ZZZ");
        DateTime dateOneWithTimeZone = formatter.parseDateTime("2014/02/26 18:52:26.626 UTC");

        DateTime dateOneWithGMT = formatter.parseDateTime("2014/02/26 00:00:00.000 UTC");
        Assertions.assertThat(DateTools.truncateTime(dateOneWithTimeZone)).isEqualTo(dateOneWithGMT);
    }

}

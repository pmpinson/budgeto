package org.pmp.budgeto.common.tools;

import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.pmp.budgeto.test.AssertTools;


public class DateToolsTest {

    @Test
    public void privateConstructor() throws Exception {
        AssertTools.onePrivateConstructorForUtilityClass(DateTools.class);
    }

    @Test
    public void toUTCWithDateNull() {
        Assertions.assertThat(DateTools.toUTC(null)).isNull();
    }

    @Test
    public void toUTCWithOtherTimeZone() {
        DateTime dateOneWithTimeZone = DateTools.FORMATTER_DATETIME_WITHZONE.parseDateTime("2014-02-26T18:52:26-05");

        DateTime dateOneWithGMT = DateTools.FORMATTER_DATETIME_WITHZONE.parseDateTime("2014-02-26T13:52:26-05");//.withZone(DateTimeZone.UTC);
        Assertions.assertThat(DateTools.toUTC(dateOneWithTimeZone)).isEqualTo(dateOneWithGMT);
    }

    @Test
    public void toUTCWithGMTTimeZone() {
        DateTime dateOneWithTimeZone = DateTools.FORMATTER_DATETIME_WITHZONE.parseDateTime("2014-02-26T18:52:26+00");

        DateTime dateOneWithGMT = DateTools.FORMATTER_DATETIME_WITHZONE.parseDateTime("2014-02-26T13:52:26+00").withZone(DateTimeZone.UTC);
        Assertions.assertThat(DateTools.toUTC(dateOneWithTimeZone)).isEqualTo(dateOneWithGMT);
    }

    @Test
    public void truncateTimeWithDateNull() {
        Assertions.assertThat(DateTools.truncateTime(null)).isNull();
    }

    @Test
    public void truncateTimeWithOtherTimeZone() {
        DateTime dateOneWithTimeZone = DateTools.FORMATTER_DATETIME_WITHZONE.parseDateTime("2014-02-26T18:52:26-06");

        DateTime dateOneWithGMT = DateTools.FORMATTER_DATETIME_WITHZONE.parseDateTime("2014-02-26T00:00:00-05");
        Assertions.assertThat(DateTools.truncateTime(dateOneWithTimeZone)).isEqualTo(dateOneWithGMT);
    }

    @Test
    public void truncateTimeWithGMTTimeZone() {
        DateTime dateOneWithTimeZone = DateTools.FORMATTER_DATETIME_WITHZONE.parseDateTime("2014-02-26T18:52:26+00");

        DateTime dateOneWithGMT = DateTools.FORMATTER_DATETIME_WITHZONE.parseDateTime("2014-02-26T00:00:00-05");
        Assertions.assertThat(DateTools.truncateTime(dateOneWithTimeZone)).isEqualTo(dateOneWithGMT);
    }

}

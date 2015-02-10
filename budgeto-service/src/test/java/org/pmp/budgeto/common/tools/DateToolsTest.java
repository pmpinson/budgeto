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
    public void toUTCDateWithTimeZone() {
        DateTime dateOneWithTimeZone = DateTools.FORMATTER_DATETIME_WITHZONE.parseDateTime("2014-02-26T18:52:26-05");

        DateTime dateOneWithGMT = DateTools.FORMATTER_DATETIME_WITHZONE.parseDateTime("2014-02-26T18:52:26Z");.withZone(DateTimeZone.UTC);
        Assertions.assertThat(DateTools.toUTCDate(dateOneWithTimeZone)).isEqualTo(dateOneWithGMT);
    }

    @Test
    public void toUTCDateWithGMTTimeZone() {
        DateTime dateOneWithTimeZone = DateTools.FORMATTER_DATETIME_WITHZONE.parseDateTime("2014-02-26T18:52:26+00");

        DateTime dateOneWithGMT = DateTools.FORMATTER_DATETIME_WITHZONE.parseDateTime("2014-02-26T18:52:26Z").withZone(DateTimeZone.UTC);
        Assertions.assertThat(DateTools.toUTCDate(dateOneWithTimeZone)).isEqualTo(dateOneWithGMT);
    }

}

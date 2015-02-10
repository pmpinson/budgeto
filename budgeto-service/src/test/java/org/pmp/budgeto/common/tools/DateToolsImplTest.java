package org.pmp.budgeto.common.tools;

import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.stereotype.Component;


@RunWith(MockitoJUnitRunner.class)
public class DateToolsImplTest {

    @InjectMocks
    private DateToolsImpl dateTools;

    @Test
    public void springConf() throws Exception {

        Assertions.assertThat(DateToolsImpl.class.getAnnotations()).hasSize(1);
        Assertions.assertThat(DateToolsImpl.class.isAnnotationPresent(Component.class)).isTrue();
    }

    @Test
    public void toUTCWithDateNull() {
        Assertions.assertThat(dateTools.toUTCDate(null)).isNull();
    }

    @Test
    public void toUTCDateWithTimeZone() {
        DateTime dateOneWithTimeZone = dateTools.getFormatterDatetimeWithzone().parseDateTime("2014-02-26T18:52:26-05");

        DateTime dateOneWithGMT = dateTools.getFormatterDatetimeWithzone().parseDateTime("2014-02-26T00:00:00Z").withZone(DateTimeZone.UTC);
        Assertions.assertThat(dateTools.toUTCDate(dateOneWithTimeZone)).isEqualTo(dateOneWithGMT);
    }

    @Test
    public void toUTCDateWithGMTTimeZone() {
        DateTime dateOneWithTimeZone = dateTools.getFormatterDatetimeWithzone().parseDateTime("2014-02-26T18:52:26+00");

        DateTime dateOneWithGMT = dateTools.getFormatterDatetimeWithzone().parseDateTime("2014-02-26T00:00:00Z").withZone(DateTimeZone.UTC);
        Assertions.assertThat(dateTools.toUTCDate(dateOneWithTimeZone)).isEqualTo(dateOneWithGMT);
    }

    public void getPatternDatetimeWithzone() {
        Assertions.assertThat(dateTools.getPatternDatetimeWithzone()).isEqualTo("yyyy-MM-dd'T'HH:mm:ssZ");
    }

    public void getFormatterDate() {
        Assertions.assertThat(dateTools.getFormatterDate()).isEqualTo(DateTimeFormat.forPattern("yyyy-MM-dd"));
    }

    public void getFormatterDatetimeWithzone() {
        Assertions.assertThat(dateTools.getFormatterDatetimeWithzone()).isEqualTo(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ"));
    }

}

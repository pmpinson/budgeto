package org.pmp.budgeto.common.tools;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;


@RunWith(MockitoJUnitRunner.class)
public class LocaleToolsImplTest {

    @InjectMocks
    private LocaleToolsImpl localeTools;

    @Test
    public void springConf() throws Exception {
        Assertions.assertThat(LocaleToolsImpl.class.getAnnotations()).hasSize(1);
        Assertions.assertThat(LocaleToolsImpl.class.isAnnotationPresent(Component.class)).isTrue();
    }

    @Test
    public void getLocale() {
        Assertions.assertThat(localeTools.getLocale()).isEqualTo(Locale.ENGLISH);
    }

}

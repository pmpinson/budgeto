package org.pmp.budgeto.common.tools;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.common.tools.TranslatorToolsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;


@RunWith(MockitoJUnitRunner.class)
public class TranslatorToolsImplTest {

    @Mock
    private MessageSource messageSource;

    @Mock
    private LocaleTools localeTools;

    @InjectMocks
    private TranslatorToolsImpl translatorTools;

    @Before
    public void setup() {
        Mockito.when(localeTools.getLocale()).thenReturn(Locale.ENGLISH);
    }

    @Test
    public void springConf() throws Exception {
        Assertions.assertThat(TranslatorToolsImpl.class.getAnnotations()).hasSize(1);
        Assertions.assertThat(TranslatorToolsImpl.class.isAnnotationPresent(Component.class)).isTrue();

        Assertions.assertThat(TranslatorToolsImpl.class.getConstructors()).hasSize(1);
        Assertions.assertThat(TranslatorToolsImpl.class.getConstructors()[0].isAnnotationPresent(Autowired.class)).isTrue();
        Assertions.assertThat(TranslatorToolsImpl.class.getConstructors()[0].getParameterAnnotations()).hasSize(2);
        Assertions.assertThat(TranslatorToolsImpl.class.getConstructors()[0].getParameterAnnotations()[0][0].annotationType()).isEqualTo(Qualifier.class);
        Assertions.assertThat(((Qualifier) TranslatorToolsImpl.class.getConstructors()[0].getParameterAnnotations()[0][0]).value()).isEqualTo("i18n");
    }

    @Test
    public void get() throws Exception {
        Mockito.when(messageSource.getMessage("message.key", new Object[]{}, Locale.FRANCE)).thenReturn("the value of message in bundle");

        String message = translatorTools.get("message.key");

        Assertions.assertThat(message).isNotNull();
        Assertions.assertThat(message).isEqualTo("the value of message in bundle");

        Mockito.verify(messageSource).getMessage("message.key", new Object[]{}, Locale.FRANCE);
        Mockito.verifyNoMoreInteractions(messageSource);
    }

    @Test
    public void getWithParams() throws Exception {
        Mockito.when(messageSource.getMessage("message.key", new Object[]{12, "toto"}, Locale.FRANCE)).thenReturn("the value of message with params in bundle");

        String message = translatorTools.get("message.key", new Object[]{12, "toto"});

        Assertions.assertThat(message).isNotNull();
        Assertions.assertThat(message).isEqualTo("the value of message with params in bundle");

        Mockito.verify(messageSource).getMessage("message.key", new Object[]{12, "toto"}, Locale.FRANCE);
        Mockito.verifyNoMoreInteractions(messageSource);
    }

}

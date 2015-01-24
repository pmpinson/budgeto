package org.pmp.budgeto.common.tools;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.test.TestTools;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@RunWith(MockitoJUnitRunner.class)
public class ToolsConfigTest {

    @InjectMocks
    private ToolsConfig toolsConfig;

    @Test
    public void springConf() throws Exception {

        Assertions.assertThat(ToolsConfig.class.getAnnotations()).hasSize(2);
        Assertions.assertThat(ToolsConfig.class.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(ToolsConfig.class.isAnnotationPresent(ComponentScan.class)).isTrue();
        Assertions.assertThat(ToolsConfig.class.getAnnotation(ComponentScan.class).basePackages()).containsExactly("org.pmp.budgeto.common.tools");

        Assertions.assertThat(ToolsConfig.class.getDeclaredMethod("messageSource", new Class[]{}).getAnnotations()).hasSize(2);
        Assertions.assertThat(ToolsConfig.class.getDeclaredMethod("messageSource", new Class[]{}).getAnnotation(Bean.class)).isNotNull();
        Assertions.assertThat(ToolsConfig.class.getDeclaredMethod("messageSource", new Class[]{}).getAnnotation(Qualifier.class)).isNotNull();
        Assertions.assertThat(ToolsConfig.class.getDeclaredMethod("messageSource", new Class[]{}).getAnnotation(Qualifier.class).value()).isEqualTo("i18n");
    }

    @Test
    public void messageSource() throws Exception {

        MessageSource messageSource = toolsConfig.messageSource();

        Assertions.assertThat(toolsConfig).isNotNull();
        Assertions.assertThat(TestTools.getField(messageSource, "basenames", String[].class)).hasSize(3);
        Assertions.assertThat(TestTools.getField(messageSource, "basenames", String[].class)).containsExactly("classpath:i18n/common", "classpath:i18n/budget", "classpath:i18n/account");
        Assertions.assertThat(TestTools.getField(messageSource, "defaultEncoding", String.class)).isEqualTo("UTF-8");
    }

}

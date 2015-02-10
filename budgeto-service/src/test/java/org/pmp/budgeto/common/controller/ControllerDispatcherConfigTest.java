package org.pmp.budgeto.common.controller;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.common.tools.DateTools;
import org.pmp.budgeto.test.TestTools;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.text.SimpleDateFormat;
import java.util.Set;


@RunWith(MockitoJUnitRunner.class)
public class ControllerDispatcherConfigTest {

    @Mock
    private DateTools dateTools;

    @InjectMocks
    private ControllerDispatcherConfig controllerDispatcherConfig;

    @Test
    public void springConf() throws Exception {

        Assertions.assertThat(ControllerDispatcherConfig.class.getAnnotations()).hasSize(3);
        Assertions.assertThat(ControllerDispatcherConfig.class.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(ControllerDispatcherConfig.class.isAnnotationPresent(EnableWebMvc.class)).isTrue();
        Assertions.assertThat(ControllerDispatcherConfig.class.isAnnotationPresent(ComponentScan.class)).isTrue();
        Assertions.assertThat(ControllerDispatcherConfig.class.getAnnotation(ComponentScan.class).basePackages()).containsExactly("org.pmp.budgeto.common.controller");
    }

    @Test
    public void jacksonConverter() {
        Mockito.when(dateTools.getPatternDatetimeWithzone()).thenReturn("yyyy-MM-dd");

        MappingJackson2HttpMessageConverter converter = controllerDispatcherConfig.jacksonConverter();

        Assertions.assertThat(converter).isNotNull();

        Assertions.assertThat(converter.getObjectMapper().isEnabled(SerializationFeature.INDENT_OUTPUT)).isTrue();
        Assertions.assertThat(converter.getObjectMapper().getDateFormat()).isEqualTo(new SimpleDateFormat(dateTools.getPatternDatetimeWithzone()));
        Assertions.assertThat(TestTools.getField(converter.getObjectMapper(), "_registeredModuleTypes", Set.class)).hasSize(1);
        Assertions.assertThat(TestTools.getField(converter.getObjectMapper(), "_registeredModuleTypes", Set.class)).contains(JodaModule.class.getName());
    }

}

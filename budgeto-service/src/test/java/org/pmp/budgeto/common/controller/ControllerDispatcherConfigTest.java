package org.pmp.budgeto.common.controller;

import org.pmp.budgeto.test.TestTools;
import org.pmp.budgeto.common.tools.DateTools;
import java.text.SimpleDateFormat;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;


@RunWith(MockitoJUnitRunner.class)
public class ControllerDispatcherConfigTest {

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
    public void jacksonFactory() {
        Jackson2ObjectMapperFactoryBean factory = controllerDispatcherConfig.jacksonFactory()
        
        Assertions.assertThat(factory).isNotNull();
        Assertions.assertThat(TestTools.get(TestTools.get(factory, dateFormat, SimpleDateFormat.class), pattern, String.class).isEqualsTo(DateTools.PATTERN_DATETIMEMS_WITHZONE);
        Assertions.assertThat(TestTools.get(factory, features, Map.class.get(SerializationFeature.INDENT_OUTPUT))).isEqualsTo(true);
    }

}

package org.pmp.budgeto.common.controller;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.common.controller.ControllerDispatcherConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


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

}

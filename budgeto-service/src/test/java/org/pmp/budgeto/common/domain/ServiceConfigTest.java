package org.pmp.budgeto.common.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@RunWith(MockitoJUnitRunner.class)
public class ServiceConfigTest {

    @InjectMocks
    private ServiceConfig serviceConfig;

    @Test
    public void springConf() throws Exception {

        Assertions.assertThat(ServiceConfig.class.getAnnotations()).hasSize(2);
        Assertions.assertThat(ServiceConfig.class.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(ServiceConfig.class.isAnnotationPresent(ComponentScan.class)).isTrue();
        Assertions.assertThat(ServiceConfig.class.getAnnotation(ComponentScan.class).basePackages()).containsExactly("org.pmp.budgeto.common.domain");
    }

}

package org.pmp.budgeto.common.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@RunWith(MockitoJUnitRunner.class)
public class DomainConfigTest {

    @InjectMocks
    private DomainConfig domainConfig;

    @Test
    public void springConf() throws Exception {

        Class<?> clazz = domainConfig.getClass();
        Assertions.assertThat(clazz.getAnnotations()).hasSize(2);
        Assertions.assertThat(clazz.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(clazz.isAnnotationPresent(ComponentScan.class)).isTrue();
        Assertions.assertThat(clazz.getAnnotation(ComponentScan.class).basePackages()).containsExactly("org.pmp.budgeto.common.domain");
    }

}

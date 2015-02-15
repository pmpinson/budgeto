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

        Assertions.assertThat(DomainConfig.class.getAnnotations()).hasSize(2);
        Assertions.assertThat(DomainConfig.class.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(DomainConfig.class.isAnnotationPresent(ComponentScan.class)).isTrue();
        Assertions.assertThat(DomainConfig.class.getAnnotation(ComponentScan.class).basePackages()).containsExactly("org.pmp.budgeto.common.domain");
    }

}

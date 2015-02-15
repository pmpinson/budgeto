package org.pmp.budgeto.app;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.common.PropertiesConfig;
import org.pmp.budgeto.common.controller.CorsFilter;
import org.pmp.budgeto.common.domain.DomainConfig;
import org.pmp.budgeto.common.repository.RepositoryConfig;
import org.pmp.budgeto.common.tools.ToolsConfig;
import org.pmp.budgeto.test.TestTools;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;


@RunWith(MockitoJUnitRunner.class)
public class WebAppConfigTest {

    @Mock
    private Environment environment;

    @InjectMocks
    private WebAppConfig webAppConfig;

    @Test
    public void springConf() throws Exception {

        Class<?> clazz = webAppConfig.getClass();
        Assertions.assertThat(clazz.getAnnotations()).hasSize(2);
        Assertions.assertThat(clazz.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(clazz.isAnnotationPresent(Import.class)).isTrue();
        Import aImport = clazz.getAnnotation(Import.class);
        Assertions.assertThat(aImport.value()).hasSize(4);
        Assertions.assertThat(aImport.value()).contains(ToolsConfig.class);
        Assertions.assertThat(aImport.value()).contains(RepositoryConfig.class);
        Assertions.assertThat(aImport.value()).contains(DomainConfig.class);
        Assertions.assertThat(aImport.value()).contains(PropertiesConfig.class);
    }

    @Test
    public void cors() {
        Mockito.when(environment.containsProperty(WebAppConfig.PROP_ALLOW_ORIGIN)).thenReturn(true);
        Mockito.when(environment.getProperty(WebAppConfig.PROP_ALLOW_ORIGIN)).thenReturn("myAllowOrigin");

        CorsFilter cors = webAppConfig.cors();

        Assertions.assertThat(cors).isNotNull();
        Assertions.assertThat(TestTools.getField(cors, "allowOrigin", String.class)).isEqualTo("myAllowOrigin");

        Mockito.verify(environment).containsProperty(Matchers.anyString());
        Mockito.verify(environment).getProperty(Matchers.anyString());
        Mockito.verifyNoMoreInteractions(environment);
    }

    @Test
    public void corsNoProps() {
        Mockito.when(environment.containsProperty(WebAppConfig.PROP_ALLOW_ORIGIN)).thenReturn(false);
        Mockito.when(environment.getProperty(WebAppConfig.PROP_ALLOW_ORIGIN)).thenReturn("myAllowOrigin");

        CorsFilter cors = webAppConfig.cors();

        Assertions.assertThat(cors).isNotNull();
        Assertions.assertThat(TestTools.getField(cors, "allowOrigin", String.class)).isNull();

        Mockito.verify(environment).containsProperty(Matchers.anyString());
        Mockito.verifyNoMoreInteractions(environment);
    }
}

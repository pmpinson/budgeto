package org.pmp.budgeto.app;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.common.PropertiesConfig;
import org.pmp.budgeto.common.domain.ServiceConfig;
import org.pmp.budgeto.common.repository.RepositoryConfig;
import org.pmp.budgeto.common.tools.ToolsConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@RunWith(MockitoJUnitRunner.class)
public class WebAppConfigTest {

    @InjectMocks
    private WebAppConfig webAppConfig;

    @Test
    public void springConf() throws Exception {

        Assertions.assertThat(WebAppConfig.class.getAnnotations()).hasSize(2);
        Assertions.assertThat(WebAppConfig.class.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(WebAppConfig.class.isAnnotationPresent(Import.class)).isTrue();
        Assertions.assertThat(WebAppConfig.class.getAnnotation(Import.class).value()).hasSize(4);
        Assertions.assertThat(WebAppConfig.class.getAnnotation(Import.class).value()).contains(ToolsConfig.class);
        Assertions.assertThat(WebAppConfig.class.getAnnotation(Import.class).value()).contains(RepositoryConfig.class);
        Assertions.assertThat(WebAppConfig.class.getAnnotation(Import.class).value()).contains(ServiceConfig.class);
        Assertions.assertThat(WebAppConfig.class.getAnnotation(Import.class).value()).contains(PropertiesConfig.class);
    }
}

package org.pmp.budgeto.common;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.common.PropertiesConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@RunWith(MockitoJUnitRunner.class)
public class PropertiesConfigTest {

    @InjectMocks
    private PropertiesConfig propertiesConfig;

    @InjectMocks
    private PropertiesConfig.ProdPropertiesConfig prodPropertiesConfig;

    @InjectMocks
    private PropertiesConfig.TestPropertiesConfig testPropertiesConfig;

    @Test
    public void springConf() throws Exception {

        Assertions.assertThat(PropertiesConfig.class.getAnnotations()).hasSize(3);
        Assertions.assertThat(PropertiesConfig.class.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(PropertiesConfig.class.isAnnotationPresent(Import.class)).isTrue();
        Assertions.assertThat(PropertiesConfig.class.getAnnotation(Import.class).value()).hasSize(2);
        Assertions.assertThat(PropertiesConfig.class.getAnnotation(Import.class).value()).contains(PropertiesConfig.ProdPropertiesConfig.class);
        Assertions.assertThat(PropertiesConfig.class.getAnnotation(Import.class).value()).contains(PropertiesConfig.TestPropertiesConfig.class);
        Assertions.assertThat(PropertiesConfig.class.isAnnotationPresent(PropertySource.class)).isTrue();
        Assertions.assertThat(PropertiesConfig.class.getAnnotation(PropertySource.class).value()).hasSize(1);
        Assertions.assertThat(PropertiesConfig.class.getAnnotation(PropertySource.class).value()).containsExactly(PropertiesConfig.CONFIG_FILE);

        Assertions.assertThat(PropertiesConfig.EnvironnementProduction.class.getAnnotations()).hasSize(3);
        Assertions.assertThat(PropertiesConfig.EnvironnementProduction.class.isAnnotationPresent(Target.class)).isTrue();
        Assertions.assertThat(PropertiesConfig.EnvironnementProduction.class.getAnnotation(Target.class).value()).containsExactly(ElementType.TYPE);
        Assertions.assertThat(PropertiesConfig.EnvironnementProduction.class.isAnnotationPresent(Retention.class)).isTrue();
        Assertions.assertThat(PropertiesConfig.EnvironnementProduction.class.getAnnotation(Retention.class).value()).isEqualTo(RetentionPolicy.RUNTIME);
        Assertions.assertThat(PropertiesConfig.EnvironnementProduction.class.isAnnotationPresent(Profile.class)).isTrue();
        Assertions.assertThat(PropertiesConfig.EnvironnementProduction.class.getAnnotation(Profile.class).value()).containsExactly(PropertiesConfig.PROD_PROFILE);

        Assertions.assertThat(PropertiesConfig.ProdPropertiesConfig.class.getAnnotations()).hasSize(3);
        Assertions.assertThat(PropertiesConfig.ProdPropertiesConfig.class.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(PropertiesConfig.ProdPropertiesConfig.class.isAnnotationPresent(PropertiesConfig.EnvironnementProduction.class)).isTrue();
        Assertions.assertThat(PropertiesConfig.ProdPropertiesConfig.class.isAnnotationPresent(PropertySource.class)).isTrue();
        Assertions.assertThat(PropertiesConfig.ProdPropertiesConfig.class.getAnnotation(PropertySource.class).value()).hasSize(1);
        Assertions.assertThat(PropertiesConfig.ProdPropertiesConfig.class.getAnnotation(PropertySource.class).value()).containsExactly(PropertiesConfig.PROD_CONFIG_FILE);

        Assertions.assertThat(PropertiesConfig.EnvironnementTest.class.getAnnotations()).hasSize(3);
        Assertions.assertThat(PropertiesConfig.EnvironnementTest.class.isAnnotationPresent(Target.class)).isTrue();
        Assertions.assertThat(PropertiesConfig.EnvironnementTest.class.getAnnotation(Target.class).value()).containsExactly(ElementType.TYPE);
        Assertions.assertThat(PropertiesConfig.EnvironnementTest.class.isAnnotationPresent(Retention.class)).isTrue();
        Assertions.assertThat(PropertiesConfig.EnvironnementTest.class.getAnnotation(Retention.class).value()).isEqualTo(RetentionPolicy.RUNTIME);
        Assertions.assertThat(PropertiesConfig.EnvironnementTest.class.isAnnotationPresent(Profile.class)).isTrue();
        Assertions.assertThat(PropertiesConfig.EnvironnementTest.class.getAnnotation(Profile.class).value()).containsExactly(PropertiesConfig.TEST_PROFILE);

        Assertions.assertThat(PropertiesConfig.TestPropertiesConfig.class.getAnnotations()).hasSize(3);
        Assertions.assertThat(PropertiesConfig.TestPropertiesConfig.class.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(PropertiesConfig.TestPropertiesConfig.class.isAnnotationPresent(PropertiesConfig.EnvironnementTest.class)).isTrue();
        Assertions.assertThat(PropertiesConfig.TestPropertiesConfig.class.isAnnotationPresent(PropertySource.class)).isTrue();
        Assertions.assertThat(PropertiesConfig.TestPropertiesConfig.class.getAnnotation(PropertySource.class).value()).hasSize(1);
        Assertions.assertThat(PropertiesConfig.TestPropertiesConfig.class.getAnnotation(PropertySource.class).value()).contains(PropertiesConfig.TEST_CONFIG_FILE);
    }

}

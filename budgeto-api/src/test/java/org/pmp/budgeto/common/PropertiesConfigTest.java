package org.pmp.budgeto.common;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
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

        Class<?> clazz = propertiesConfig.getClass();
        Assertions.assertThat(clazz.getAnnotations()).hasSize(3);
        Assertions.assertThat(clazz.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(clazz.isAnnotationPresent(Import.class)).isTrue();
        Assertions.assertThat(clazz.getAnnotation(Import.class).value()).hasSize(2);
        Assertions.assertThat(clazz.getAnnotation(Import.class).value()).contains(PropertiesConfig.ProdPropertiesConfig.class);
        Assertions.assertThat(clazz.getAnnotation(Import.class).value()).contains(PropertiesConfig.TestPropertiesConfig.class);
        Assertions.assertThat(clazz.isAnnotationPresent(PropertySource.class)).isTrue();
        Assertions.assertThat(clazz.getAnnotation(PropertySource.class).value()).hasSize(1);
        Assertions.assertThat(clazz.getAnnotation(PropertySource.class).value()).containsExactly(PropertiesConfig.CONFIG_FILE);

        clazz = PropertiesConfig.EnvironnementProduction.class;
        Assertions.assertThat(clazz.getAnnotations()).hasSize(3);
        Assertions.assertThat(clazz.isAnnotationPresent(Target.class)).isTrue();
        Assertions.assertThat(clazz.getAnnotation(Target.class).value()).containsExactly(ElementType.TYPE);
        Assertions.assertThat(clazz.isAnnotationPresent(Retention.class)).isTrue();
        Assertions.assertThat(clazz.getAnnotation(Retention.class).value()).isEqualTo(RetentionPolicy.RUNTIME);
        Assertions.assertThat(clazz.isAnnotationPresent(Profile.class)).isTrue();
        Assertions.assertThat(clazz.getAnnotation(Profile.class).value()).containsExactly(PropertiesConfig.PROD_PROFILE);

        clazz = prodPropertiesConfig.getClass();
        Assertions.assertThat(clazz.getAnnotations()).hasSize(3);
        Assertions.assertThat(clazz.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(clazz.isAnnotationPresent(PropertiesConfig.EnvironnementProduction.class)).isTrue();
        Assertions.assertThat(clazz.isAnnotationPresent(PropertySource.class)).isTrue();
        Assertions.assertThat(clazz.getAnnotation(PropertySource.class).value()).hasSize(3);
        Assertions.assertThat(clazz.getAnnotation(PropertySource.class).value()).containsExactly(PropertiesConfig.PROD_CONFIG_FILE, PropertiesConfig.EXTERNAL_FILE, PropertiesConfig.EXTERNAL_OVERRIDE_FILE);
        Assertions.assertThat(clazz.getAnnotation(PropertySource.class).ignoreResourceNotFound()).isTrue();

        clazz = PropertiesConfig.EnvironnementTest.class;
        Assertions.assertThat(clazz.getAnnotations()).hasSize(3);
        Assertions.assertThat(clazz.isAnnotationPresent(Target.class)).isTrue();
        Assertions.assertThat(clazz.getAnnotation(Target.class).value()).containsExactly(ElementType.TYPE);
        Assertions.assertThat(clazz.isAnnotationPresent(Retention.class)).isTrue();
        Assertions.assertThat(clazz.getAnnotation(Retention.class).value()).isEqualTo(RetentionPolicy.RUNTIME);
        Assertions.assertThat(clazz.isAnnotationPresent(Profile.class)).isTrue();
        Assertions.assertThat(clazz.getAnnotation(Profile.class).value()).containsExactly(PropertiesConfig.TEST_PROFILE);

        clazz = testPropertiesConfig.getClass();
        Assertions.assertThat(clazz.getAnnotations()).hasSize(3);
        Assertions.assertThat(clazz.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(clazz.isAnnotationPresent(PropertiesConfig.EnvironnementTest.class)).isTrue();
        Assertions.assertThat(clazz.isAnnotationPresent(PropertySource.class)).isTrue();
        Assertions.assertThat(clazz.getAnnotation(PropertySource.class).value()).hasSize(3);
        Assertions.assertThat(clazz.getAnnotation(PropertySource.class).value()).contains(PropertiesConfig.TEST_CONFIG_FILE, PropertiesConfig.EXTERNAL_FILE, PropertiesConfig.EXTERNAL_OVERRIDE_FILE);
        Assertions.assertThat(clazz.getAnnotation(PropertySource.class).ignoreResourceNotFound()).isTrue();
    }

}

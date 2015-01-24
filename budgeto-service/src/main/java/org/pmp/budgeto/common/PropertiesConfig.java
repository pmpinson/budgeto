package org.pmp.budgeto.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * config the properties to config application
 * 2 configs : test and prod
 * PropertiesConfig.CONFIG_FILE are load by default, other properties classpath:environnement-${profile}.properties are load by spring.active.profile property
 * the default profil is prod
 * 2 annotions are defined : EnvironnementProduction and EnvironnementTest to enabled some bean or configuration by selected environnement
 */
@Configuration
@PropertySource(PropertiesConfig.CONFIG_FILE)
@Import({PropertiesConfig.ProdPropertiesConfig.class, PropertiesConfig.TestPropertiesConfig.class})
public class PropertiesConfig {

    public static final String CONFIG_FILE = "classpath:environnement.properties";

    public static final String PROD_PROFILE = "prod";

    public static final String PROD_CONFIG_FILE = "classpath:environnement-" + PROD_PROFILE + ".properties";

    public static final String TEST_PROFILE = "test";

    public static final String TEST_CONFIG_FILE = "classpath:environnement-" + TEST_PROFILE + ".properties";

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Profile(PROD_PROFILE)
    public @interface EnvironnementProduction {
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Profile(TEST_PROFILE)
    public @interface EnvironnementTest {
    }

    @Configuration
    @EnvironnementProduction
    @PropertySource(PROD_CONFIG_FILE)
    public static class ProdPropertiesConfig {
    }

    @Configuration
    @EnvironnementTest
    @PropertySource(TEST_CONFIG_FILE)
    public static class TestPropertiesConfig {
    }

}

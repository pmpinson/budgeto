package org.pmp.budgeto.app;

import org.pmp.budgeto.common.PropertiesConfig;
import org.pmp.budgeto.common.controller.CorsFilter;
import org.pmp.budgeto.common.domain.DomainConfig;
import org.pmp.budgeto.common.repository.RepositoryConfig;
import org.pmp.budgeto.common.tools.ToolsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

/**
 * to configure the web app context with all component architecture
 */
@Configuration
@Import({PropertiesConfig.class, ToolsConfig.class, RepositoryConfig.class, DomainConfig.class})
public class WebAppConfig {

    private static final String PROP_APP_BASE = "app";
    public static final String PROP_ALLOW_ORIGIN = PROP_APP_BASE + ".allow.origin";

    @Autowired
    private Environment environment;

    @Bean
    public CorsFilter cors() {
        String allowOrigin = null;
        if (environment.containsProperty(PROP_ALLOW_ORIGIN)) {
            allowOrigin = environment.getProperty(PROP_ALLOW_ORIGIN);
        }
        return new CorsFilter(allowOrigin);
    }

}

package org.pmp.budgeto.app;

import org.pmp.budgeto.common.PropertiesConfig;
import org.pmp.budgeto.common.controller.CorsFilter;
import org.pmp.budgeto.common.domain.ServiceConfig;
import org.pmp.budgeto.common.repository.RepositoryConfig;
import org.pmp.budgeto.common.tools.ToolsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

/**
 * to configure the web app context with all component architecture
 */
@Configuration
@Import({PropertiesConfig.class, ToolsConfig.class, RepositoryConfig.class, ServiceConfig.class})
public class WebAppConfig {

    @Autowired
    private Environment environment;

    @Bean
    public CorsFilter cors() {
        String allowOrigin = null;
        if (environment.containsProperty("app.allow.origin")){
            allowOrigin = environment.getProperty("app.allow.origin");
        }
        return new CorsFilter(allowOrigin);
    }

}

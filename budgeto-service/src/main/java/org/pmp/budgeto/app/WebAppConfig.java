package org.pmp.budgeto.app;

import org.pmp.budgeto.common.repository.RepositoryConfig;
import org.pmp.budgeto.common.domain.ServiceConfig;
import org.pmp.budgeto.common.PropertiesConfig;
import org.pmp.budgeto.common.tools.ToolsConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * to configure the web app context with all component architecture
 */
@Configuration
@Import({PropertiesConfig.class, ToolsConfig.class, RepositoryConfig.class, ServiceConfig.class})
public class WebAppConfig {

}

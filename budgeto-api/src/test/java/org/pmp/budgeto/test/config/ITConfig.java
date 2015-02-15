package org.pmp.budgeto.test.config;

import org.pmp.budgeto.common.PropertiesConfig;
import org.pmp.budgeto.common.domain.DomainConfig;
import org.pmp.budgeto.common.repository.RepositoryConfig;
import org.pmp.budgeto.common.tools.ToolsConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * Config of the web context for it test
 */
@Configuration
@Import({PropertiesConfig.class, ToolsConfig.class, RepositoryConfig.class, DomainConfig.class})
public class ITConfig {
}

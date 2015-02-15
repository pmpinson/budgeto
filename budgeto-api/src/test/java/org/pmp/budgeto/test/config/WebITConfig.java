package org.pmp.budgeto.test.config;

import org.pmp.budgeto.common.controller.ControllerDispatcherConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * Config of the web context for it test
 */
@Configuration
@Import({ITConfig.class, ControllerDispatcherConfig.class})
public class WebITConfig {
}

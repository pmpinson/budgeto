package org.pmp.budgeto.common.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * to configure the rest app servlet dispatcher
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.pmp.budgeto.common.controller"})
public class ControllerDispatcherConfig {

}

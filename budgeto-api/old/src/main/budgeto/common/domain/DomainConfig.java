package org.pmp.budgeto.common.domain;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * config the domain components for inject
 * scan on org.pmp.budgeto.common.domain package
 */
@Configuration
@ComponentScan(basePackages = "org.pmp.budgeto.common.domain")
public class DomainConfig {

}

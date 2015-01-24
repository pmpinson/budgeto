package org.pmp.budgeto.domain.budget;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * config the domain components for inject
 * scan on org.pmp.budgeto.common.domain package
 */
@Configuration
@EnableMongoRepositories(basePackages = "org.pmp.budgeto.domain.budget")
@ComponentScan(basePackages = "org.pmp.budgeto.domain.budget")
public class BudgetConfig {

}

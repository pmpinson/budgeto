package org.pmp.budgeto.domain.budget;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.domain.budget.BudgetConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@RunWith(MockitoJUnitRunner.class)
public class BudgetConfigTest {

    @InjectMocks
    private org.pmp.budgeto.domain.budget.BudgetConfig BudgetConfig;

    @Test
    public void springConf() throws Exception {

        Assertions.assertThat(BudgetConfig.class.getAnnotations()).hasSize(3);
        Assertions.assertThat(BudgetConfig.class.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(BudgetConfig.class.isAnnotationPresent(EnableMongoRepositories.class)).isTrue();
        Assertions.assertThat(BudgetConfig.class.getAnnotation(EnableMongoRepositories.class).basePackages()).containsExactly("org.pmp.budgeto.domain.budget");
        Assertions.assertThat(BudgetConfig.class.isAnnotationPresent(ComponentScan.class)).isTrue();
        Assertions.assertThat(BudgetConfig.class.getAnnotation(ComponentScan.class).basePackages()).containsExactly("org.pmp.budgeto.domain.budget");
    }

}

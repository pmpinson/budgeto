package org.pmp.budgeto.domain.budget;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@RunWith(MockitoJUnitRunner.class)
public class BudgetConfigTest {

    @InjectMocks
    private BudgetConfig budgetConfig;

    @Test
    public void springConf() throws Exception {

        Class<?> clazz = budgetConfig.getClass();
        Assertions.assertThat(clazz.getAnnotations()).hasSize(3);
        Assertions.assertThat(clazz.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(clazz.isAnnotationPresent(EnableMongoRepositories.class)).isTrue();
        Assertions.assertThat(clazz.getAnnotation(EnableMongoRepositories.class).basePackages()).containsExactly("org.pmp.budgeto.domain.budget");
        Assertions.assertThat(clazz.isAnnotationPresent(ComponentScan.class)).isTrue();
        Assertions.assertThat(clazz.getAnnotation(ComponentScan.class).basePackages()).containsExactly("org.pmp.budgeto.domain.budget");
    }

}

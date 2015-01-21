package org.pmp.budgeto.domain.budget;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.pmp.budgeto.domain.budget.BudgetRepository;
import org.pmp.budgeto.domain.budget.Budget;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public class BudgetRepositoryTest {

    @Test
    public void structure() throws Exception {
        Assertions.assertThat(BudgetRepository.class.getInterfaces()).hasSize(1);
        Assertions.assertThat(BudgetRepository.class.getInterfaces()).contains(MongoRepository.class);
        Assertions.assertThat(BudgetRepository.class.getGenericInterfaces()[0].toString()).isEqualTo(MongoRepository.class.getName() + "<" + Budget.class.getName() + ", " + Long.class.getName() + ">");
    }

    @Test
    public void springConf() throws Exception {

        Assertions.assertThat(BudgetRepository.class.getAnnotations()).hasSize(1);
        Assertions.assertThat(BudgetRepository.class.isAnnotationPresent(Repository.class)).isTrue();
    }

}

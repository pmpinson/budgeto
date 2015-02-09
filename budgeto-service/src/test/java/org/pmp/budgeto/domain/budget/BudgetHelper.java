package org.pmp.budgeto.domain.budget;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * helper to init data on Budget object
 */
@Component
public class BudgetHelper {

    @Autowired
    private BudgetRepository budgetRepository;

    public void init() throws Exception {

        budgetRepository.deleteAll();

        Budget object = new Budget().setName("budget1").setNote("a note on first");
        budgetRepository.save(object);

        object = new Budget().setName("budget2").setNote("a note on second");
        budgetRepository.save(object);
    }

    public Budget findByName(List<Budget> objects, String name) {
        for (Budget object : objects) {
            if (object.getName().equals(name)) {
                return object;
            }
        }
        Assert.fail("budget " + name + " not ");
        return null;
    }

    public void controlBudget1(Budget object) {
        Assertions.assertThat(object.getName()).isEqualTo("budget1");
        Assertions.assertThat(object.getNote()).isEqualTo("a note on first");
    }

    public void controlBudget2(Budget object) {
        Assertions.assertThat(object.getName()).isEqualTo("budget2");
        Assertions.assertThat(object.getNote()).isEqualTo("a note on second");
    }

    public long nbBudgets() throws Exception {

        return budgetRepository.count();
    }

}

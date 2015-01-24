package org.pmp.budgeto.domain.budget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * helper to init data on Budget object
 */
@Component
public class BudgetHelper {

    @Autowired
    private BudgetRepository budgetRepository;

    public void init() throws Exception {

        budgetRepository.deleteAll();

        Budget object = new Budget().setName("budget1");
        budgetRepository.save(object);

        object = new Budget().setName("budget2");
        budgetRepository.save(object);
    }

    public long nbBudgets() throws Exception {

        return budgetRepository.count();
    }

}

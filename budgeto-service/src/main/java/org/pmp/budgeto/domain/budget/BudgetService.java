package org.pmp.budgeto.domain.budget;

import org.pmp.budgeto.common.domain.DomainException;

import java.util.List;

/**
 * manager of budget
 */
public interface BudgetService {

    /**
     * find all budgets
     *
     * @return a list of budgets
     */
    List<Budget> findAll();

    /**
     * add a new budget
     *
     * @param object the object to add
     * @return the new budget
     * @throws org.pmp.budgeto.common.domain.DomainException if error during validation or add
     */
    Budget add(Budget object) throws DomainException;

}

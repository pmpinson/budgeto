package org.pmp.budgeto.domain.budget;

import org.pmp.budgeto.common.domain.DomainException;

import java.util.Set;

/**
 * manager of budget
 */
public interface BudgetDomain {

    /**
     * find all budgets
     *
     * @return a set of budgets
     */
    Set<Budget> findAll();

    /**
     * add a new budget
     *
     * @param object the object to add
     * @return the new budget
     * @throws org.pmp.budgeto.common.domain.DomainException if error during validation or add
     */
    Budget add(Budget object) throws DomainException;

}

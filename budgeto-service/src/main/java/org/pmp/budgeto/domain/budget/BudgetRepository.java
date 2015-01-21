package org.pmp.budgeto.domain.budget;

import org.pmp.budgeto.domain.budget.Budget;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * repository to work with budget into mongo db
 */
@Repository
public interface BudgetRepository extends MongoRepository<Budget, Long> {

}

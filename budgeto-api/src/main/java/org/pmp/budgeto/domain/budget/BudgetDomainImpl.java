package org.pmp.budgeto.domain.budget;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.Validate;
import org.pmp.budgeto.common.domain.DomainConflictException;
import org.pmp.budgeto.common.domain.DomainException;
import org.pmp.budgeto.common.domain.DomainTools;
import org.pmp.budgeto.common.domain.DomainValidationError;
import org.pmp.budgeto.common.tools.TranslatorTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BudgetDomainImpl implements BudgetDomain {

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetDomainImpl.class);

    private final BudgetRepository budgetRepository;

    private final TranslatorTools translatorTools;

    private final DomainTools domainTools;

    @Autowired
    public BudgetDomainImpl(BudgetRepository budgetRepository, TranslatorTools translatorTools, DomainTools domainTools) {
        this.budgetRepository = Validate.notNull(budgetRepository);
        this.translatorTools = Validate.notNull(translatorTools);
        this.domainTools = Validate.notNull(domainTools);
    }

    @Override
    public Set<Budget> findAll() {
        LOGGER.info("ask for all budget");
        return Sets.newHashSet(budgetRepository.findAll());
    }

    @Override
    public Budget add(Budget object) throws DomainException {
        LOGGER.info("ask for add a new budget");

        domainTools.validate(object, "budget.object");

        LOGGER.info("add a new budget, after validation");
        try {
            return budgetRepository.save(object);
        } catch (DataAccessException e) {
            if (domainTools.isConstraintViolationExceptionFor(e, Budget.UNIQUE_IDX_NAME)) {
                DomainValidationError validationError = new DomainValidationError("name", translatorTools.get("budget.exist.same.name", object.getName()));
                throw new DomainConflictException(translatorTools.get("object.constraint.error", translatorTools.get("budget.object")), e, validationError);
            } else {
                throw e;
            }
        }
    }

}

package org.pmp.budgeto.domain.account;

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
public class AccountDomainImpl implements AccountDomain {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDomainImpl.class);

    private final AccountRepository accountRepository;

    private final TranslatorTools translatorTools;

    private final DomainTools domainTools;

    @Autowired
    public AccountDomainImpl(AccountRepository accountRepository, TranslatorTools translatorTools, DomainTools domainTools) {
        this.accountRepository = Validate.notNull(accountRepository);
        this.translatorTools = Validate.notNull(translatorTools);
        this.domainTools = Validate.notNull(domainTools);
    }

    @Override
    public Set<Account> findAll() {
        LOGGER.info("ask for all account");
        return Sets.newHashSet(accountRepository.findAll());
    }

    @Override
    public Account find(String name) {
        LOGGER.info("ask for find account {}", name);
        return accountRepository.findByName(name);
    }

    @Override
    public Account add(Account object) throws DomainException {
        LOGGER.info("ask for add a new account");

        domainTools.validate(object, "account.object");

        LOGGER.info("add a new account, after validation");
        try {
            return accountRepository.save(object);
        } catch (DataAccessException e) {
            if (domainTools.isConstraintViolationExceptionFor(e, Account.UNIQUE_IDX_NAME)) {
                DomainValidationError validationError = new DomainValidationError("name", translatorTools.get("account.exist.same.name", object.getName()));
                throw new DomainConflictException(translatorTools.get("object.constraint.error", translatorTools.get("account.object")), e, validationError);
            } else {
                throw e;
            }
        }
    }

}

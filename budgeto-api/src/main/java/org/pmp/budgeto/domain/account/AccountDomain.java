package org.pmp.budgeto.domain.account;

import org.pmp.budgeto.common.domain.DomainException;

import java.util.Set;

/**
 * manager of account
 */
public interface AccountDomain {

    /**
     * find all accounts
     *
     * @return a set of accounts
     */
    Set<Account> findAll();

    /**
     * find an account by name
     *
     * @param name the name of account to find
     * @return an account
     */
    Account find(String name);

    /**
     * add a new account
     *
     * @param object the object to add
     * @return the new account
     * @throws org.pmp.budgeto.common.domain.DomainException if error during validation or add
     */
    Account add(Account object) throws DomainException;

}

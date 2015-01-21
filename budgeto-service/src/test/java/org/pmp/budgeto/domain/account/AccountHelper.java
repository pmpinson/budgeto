package org.pmp.budgeto.domain.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * helper to init data on Budget object
 */
@Component
public class AccountHelper {

    @Autowired
    private AccountRepository accountRepository;

    public void init() throws Exception {

        accountRepository.deleteAll();

        Account object = new Account().setName("account1");
        accountRepository.save(object);

        object = new Account().setName("account2");
        accountRepository.save(object);
    }

    public long nbAccounts() throws Exception {

        return accountRepository.count();
    }

}

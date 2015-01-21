package org.pmp.budgeto.domain.account;

import org.pmp.budgeto.domain.account.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * repository to work with account into mongo db
 */
@Repository
public interface AccountRepository extends MongoRepository<Account, Long> {

    Account findByName(String name);

}

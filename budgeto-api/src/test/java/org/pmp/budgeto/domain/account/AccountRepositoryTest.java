package org.pmp.budgeto.domain.account;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public class AccountRepositoryTest {

    @Test
    public void structure() throws Exception {
        Assertions.assertThat(AccountRepository.class.getInterfaces()).hasSize(1);
        Assertions.assertThat(AccountRepository.class.getInterfaces()).contains(MongoRepository.class);
        Assertions.assertThat(AccountRepository.class.getGenericInterfaces()[0].toString()).isEqualTo(MongoRepository.class.getName() + "<" + Account.class.getName() + ", " + Long.class.getName() + ">");
    }

    @Test
    public void springConf() throws Exception {

        Assertions.assertThat(AccountRepository.class.getAnnotations()).hasSize(1);
        Assertions.assertThat(AccountRepository.class.isAnnotationPresent(Repository.class)).isTrue();
    }

}

package org.pmp.budgeto.domain.account;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public class AccountRepositoryTest {

    @Test
    public void structure() throws Exception {

        Class<?> clazz = AccountRepository.class;
        Assertions.assertThat(clazz.getInterfaces()).hasSize(1);
        Assertions.assertThat(clazz.getInterfaces()).contains(MongoRepository.class);
        Assertions.assertThat(clazz.getGenericInterfaces()[0].toString()).isEqualTo(MongoRepository.class.getName() + "<" + Account.class.getName() + ", " + Long.class.getName() + ">");
    }

    @Test
    public void springConf() throws Exception {

        Class<?> clazz = AccountRepository.class;
        Assertions.assertThat(clazz.getAnnotations()).hasSize(1);
        Assertions.assertThat(clazz.isAnnotationPresent(Repository.class)).isTrue();
    }

}

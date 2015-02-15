package org.pmp.budgeto.domain.account;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@RunWith(MockitoJUnitRunner.class)
public class AccountConfigTest {

    @InjectMocks
    private org.pmp.budgeto.domain.account.AccountConfig AccountConfig;

    @Test
    public void springConf() throws Exception {

        Assertions.assertThat(AccountConfig.class.getAnnotations()).hasSize(3);
        Assertions.assertThat(AccountConfig.class.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(AccountConfig.class.isAnnotationPresent(EnableMongoRepositories.class)).isTrue();
        Assertions.assertThat(AccountConfig.class.getAnnotation(EnableMongoRepositories.class).basePackages()).containsExactly("org.pmp.budgeto.domain.account");
        Assertions.assertThat(AccountConfig.class.isAnnotationPresent(ComponentScan.class)).isTrue();
        Assertions.assertThat(AccountConfig.class.getAnnotation(ComponentScan.class).basePackages()).containsExactly("org.pmp.budgeto.domain.account");
    }

}

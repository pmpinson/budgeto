package org.pmp.budgeto.domain.account;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.common.tools.DateTools;
import org.pmp.budgeto.common.tools.DateToolsImpl;
import org.pmp.budgeto.test.TestTools;
import org.pmp.budgeto.test.config.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@RunWith(MockitoJUnitRunner.class)
public class AccountListenerTest {

    private AccountListener accountListener;

    @Before
    public void setup() {
        accountListener = new AccountListener(TestConfig.dateTools);
    }

    @Test
    public void springConf() throws Exception {

        Class<?> clazz = accountListener.getClass();
        Assertions.assertThat(clazz.getAnnotations()).hasSize(1);
        Assertions.assertThat(clazz.isAnnotationPresent(Component.class)).isTrue();

        Assertions.assertThat(clazz.getConstructors()).hasSize(1);
        Assertions.assertThat(clazz.getConstructors()[0].isAnnotationPresent(Autowired.class)).isTrue();
    }

    @Test
    public void onAfterConvert() throws Exception {
        DateTools otherDateTools = new DateToolsImpl();
        Operation ope = new Operation(otherDateTools);
        Account account = new Account().addOperations(ope);

        Assertions.assertThat(TestTools.getField(ope, "dateTools", DateTools.class)).isEqualTo(otherDateTools);
        accountListener.onAfterConvert(null, account);

        Assertions.assertThat(TestTools.getField(ope, "dateTools", DateTools.class)).isEqualTo(TestConfig.dateTools);
    }

}

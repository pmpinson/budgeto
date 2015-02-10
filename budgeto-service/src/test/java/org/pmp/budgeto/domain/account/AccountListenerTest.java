package org.pmp.budgeto.domain.account;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponses;
import junit.framework.Assert;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RunWith(MockitoJUnitRunner.class)
public class AccountListenerTest {

    private AccountListener accountListener;

    @Before
    public void setup() {
        accountListener = new AccountListener(TestConfig.dateTools);
    }

    @Test
    public void springConf() throws Exception {
        Assertions.assertThat(AccountListener.class.getAnnotations()).hasSize(1);
        Assertions.assertThat(AccountListener.class.isAnnotationPresent(Component.class)).isTrue();

        Assertions.assertThat(AccountController.class.getConstructors()).hasSize(1);
        Assertions.assertThat(AccountController.class.getConstructors()[0].isAnnotationPresent(Autowired.class)).isTrue();
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

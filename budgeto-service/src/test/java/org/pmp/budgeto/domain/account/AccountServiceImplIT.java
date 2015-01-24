package org.pmp.budgeto.domain.account;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.pmp.budgeto.common.domain.DomainConflictException;
import org.pmp.budgeto.common.domain.DomainException;
import org.pmp.budgeto.test.config.ITConfig;
import org.pmp.budgeto.test.matcher.ServiceExceptionValidationErrorContentMatcher;
import org.pmp.budgeto.test.matcher.ServiceExceptionValidationErrorsSizeMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class, AccountConfig.class})
@ActiveProfiles("test")
public class AccountServiceImplIT {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private AccountHelper accountHelper;

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void setup() throws Exception {

        accountHelper.init();
    }

    @Test
    public void findAll() throws Exception {

        List<Account> objects = accountService.findAll();

        Assertions.assertThat(objects).hasSize(2);
        Assertions.assertThat(objects).extracting("name").containsExactly("account1", "account2");
    }

    @Test
    public void add() throws Exception {

        Account object = new Account().setName("my account to add");

        Account newObject = accountService.add(object);

        Assertions.assertThat(accountHelper.nbAccounts()).isEqualTo(3);
        Assertions.assertThat(newObject.getName()).isEqualTo("my account to add");
    }

    @Test
    public void addWithValidationError() throws Exception {

        Account object = new Account();

        expectedException.expect(DomainException.class);
        expectedException.expectMessage("object account not valid");

        accountService.add(object);
    }

    @Test
    public void addWithSameName() throws Exception {

        Account object = new Account().setName("account2");

        expectedException.expect(DomainConflictException.class);
        expectedException.expectMessage("error on a constraint during add of account");
        expectedException.expect(new ServiceExceptionValidationErrorsSizeMatcher(1));
        expectedException.expect(new ServiceExceptionValidationErrorContentMatcher("name", new String[]{"an account (account2) already exist with same name"}));

        accountService.add(object);
    }

    @Test
    public void find() throws Exception {
        Account account = accountService.find("account2");
        System.out.println(account);
    }

}

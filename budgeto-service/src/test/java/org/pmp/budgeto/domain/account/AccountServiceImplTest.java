package org.pmp.budgeto.domain.account;

import com.google.common.collect.Lists;
import com.mongodb.DuplicateKeyException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.common.domain.DomainConflictException;
import org.pmp.budgeto.common.domain.DomainException;
import org.pmp.budgeto.common.domain.DomainTools;
import org.pmp.budgeto.test.config.TestConfig;
import org.pmp.budgeto.common.tools.TranslatorTools;
import org.pmp.budgeto.common.tools.ValidatorTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private AccountRepository accountRepository;

    private TranslatorTools translatorTools = TestConfig.translatorTools();

    private ValidatorTools validatorTools = TestConfig.validatorTools();

    private DomainTools domainTools = TestConfig.serviceExceptionTools();

    private AccountService accountService;

    @Before
    public void setup() {
        accountService = new AccountServiceImpl(accountRepository, TestConfig.translatorTools(), TestConfig.validatorTools(), TestConfig.serviceExceptionTools());
    }

    @Test
    public void springConf() throws Exception {
        Assertions.assertThat(AccountServiceImpl.class.getAnnotations()).hasSize(1);
        Assertions.assertThat(AccountServiceImpl.class.isAnnotationPresent(Service.class)).isTrue();

        Assertions.assertThat(AccountServiceImpl.class.getConstructors()).hasSize(1);
        Assertions.assertThat(AccountServiceImpl.class.getConstructors()[0].isAnnotationPresent(Autowired.class)).isTrue();
    }

    @Test
    public void findAll() throws Exception {

        Account object1 = new Account().setName("account1");
        Account object2 = new Account().setName("account2");
        Mockito.when(accountRepository.findAll()).thenReturn(Lists.newArrayList(object1, object2));

        List<Account> objects = accountService.findAll();

        Assertions.assertThat(objects).hasSize(2);
        Mockito.verify(accountRepository).findAll();
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void add() throws Exception {

        Account object = new Account().setName("my account to add");
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(object);

        accountService.add(object);

        Mockito.verify(accountRepository).save(Mockito.any(Account.class));
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void addObjectNull() throws Exception {

        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("empty object account");

        accountService.add(null);

        Mockito.verify(accountRepository).save(Mockito.any(Account.class));
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void addWithValidationError() throws Exception {

        Account object = new Account();

        expectedException.expect(DomainException.class);
        expectedException.expectMessage("object account not valid");

        accountService.add(object);

        Mockito.verify(accountRepository).save(Mockito.any(Account.class));
        Mockito.verifyNoMoreInteractions(accountRepository, accountService);
    }

    @Test
    public void addUnknownDataAccessException() throws Exception {

        Account object = new Account().setName("my account to add");
        DataAccessException ex = Mockito.mock(DataAccessException.class);
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenThrow(ex);

        expectedException.expect(DataAccessException.class);

        accountService.add(object);

        Mockito.verify(accountRepository).save(Mockito.any(Account.class));
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void addDataAccessException() throws Exception {

        Account object = new Account().setName("my account to add");
        DataAccessException ex = Mockito.mock(DataAccessException.class);
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenThrow(ex);
        DuplicateKeyException dupEx = Mockito.mock(DuplicateKeyException.class);
        Mockito.when(ex.getCause()).thenReturn(dupEx);
        Mockito.when(ex.getMessage()).thenReturn("error on accountUniqueName");

        expectedException.expect(DomainConflictException.class);

        accountService.add(object);

        Mockito.verify(accountRepository).save(Mockito.any(Account.class));
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

}

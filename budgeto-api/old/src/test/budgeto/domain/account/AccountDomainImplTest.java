package org.pmp.budgeto.domain.account;

import com.google.common.collect.Lists;
import com.mongodb.DuplicateKeyException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.common.domain.DomainConflictException;
import org.pmp.budgeto.common.domain.DomainException;
import org.pmp.budgeto.test.config.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Set;


@RunWith(MockitoJUnitRunner.class)
public class AccountDomainImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private AccountRepository accountRepository;

    private AccountDomain accountDomain;

    @Before
    public void setup() {
        accountDomain = new AccountDomainImpl(accountRepository, TestConfig.translatorTools, TestConfig.domainTools);
    }

    @Test
    public void springConf() throws Exception {

        Class<?> clazz = accountDomain.getClass();
        Assertions.assertThat(clazz.getAnnotations()).hasSize(1);
        Assertions.assertThat(clazz.isAnnotationPresent(Service.class)).isTrue();

        Assertions.assertThat(clazz.getConstructors()).hasSize(1);
        Assertions.assertThat(clazz.getConstructors()[0].isAnnotationPresent(Autowired.class)).isTrue();
    }

    @Test
    public void findAll() throws Exception {

        Account object1 = new Account().setName("account1");
        Account object2 = new Account().setName("account2");
        Mockito.when(accountRepository.findAll()).thenReturn(Lists.newArrayList(object1, object2));

        Set<Account> objects = accountDomain.findAll();

        Assertions.assertThat(objects).hasSize(2);

        Mockito.verify(accountRepository).findAll();
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void findNull() throws Exception {
        Account object = new Account().setName("accountYYYY");
        Mockito.when(accountRepository.findByName(Matchers.anyString())).thenReturn(null);
        Mockito.when(accountRepository.findByName("accountYYYY")).thenReturn(object);

        Account account = accountDomain.find("accountXXX");

        Assertions.assertThat(account).isNull();

        Mockito.verify(accountRepository).findByName("accountXXX");
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void find() throws Exception {
        Account object = new Account().setName("accountYYYY");
        Mockito.when(accountRepository.findByName(Matchers.anyString())).thenReturn(null);
        Mockito.when(accountRepository.findByName("accountYYYY")).thenReturn(object);

        Account account = accountDomain.find("accountYYYY");

        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getName()).isEqualTo("accountYYYY");

        Mockito.verify(accountRepository).findByName("accountYYYY");
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void add() throws Exception {

        Account object = new Account().setName("my account to add");
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(object);

        accountDomain.add(object);

        Mockito.verify(accountRepository).save(Mockito.any(Account.class));
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void addObjectNull() throws Exception {

        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("empty object account");

        accountDomain.add(null);

        Mockito.verify(accountRepository).save(Mockito.any(Account.class));
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void addWithValidationError() throws Exception {

        Account object = new Account();

        expectedException.expect(DomainException.class);
        expectedException.expectMessage("object account not valid");

        accountDomain.add(object);

        Mockito.verify(accountRepository).save(Mockito.any(Account.class));
        Mockito.verifyNoMoreInteractions(accountRepository, accountDomain);
    }

    @Test
    public void addUnknownDataAccessException() throws Exception {

        Account object = new Account().setName("my account to add");
        DataAccessException ex = Mockito.mock(DataAccessException.class);
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenThrow(ex);

        expectedException.expect(DataAccessException.class);

        accountDomain.add(object);

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

        accountDomain.add(object);

        Mockito.verify(accountRepository).save(Mockito.any(Account.class));
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

}

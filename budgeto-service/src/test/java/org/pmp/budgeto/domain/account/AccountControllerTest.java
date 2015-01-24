package org.pmp.budgeto.domain.account;

import com.google.common.collect.Lists;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponses;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private AccountService accountService;

    private AccountController accountController;

    private Account result;

    @Before
    public void setup() {
        accountController = new AccountController(accountService);
    }

    @Test
    public void springConf() throws Exception {
        Assertions.assertThat(AccountController.class.getAnnotations()).hasSize(3);
        Assertions.assertThat(AccountController.class.isAnnotationPresent(RestController.class)).isTrue();
        Assertions.assertThat(AccountController.class.isAnnotationPresent(Api.class)).isTrue();
        Assertions.assertThat(AccountController.class.isAnnotationPresent(RequestMapping.class)).isTrue();
        Assertions.assertThat(AccountController.class.getAnnotation(RequestMapping.class).value()).containsOnly("account");

        Assertions.assertThat(AccountController.class.getConstructors()).hasSize(1);
        Assertions.assertThat(AccountController.class.getConstructors()[0].isAnnotationPresent(Autowired.class)).isTrue();

        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotations()).hasSize(4);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(ApiOperation.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(ApiResponses.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(RequestMapping.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(RequestMapping.class).value()).containsOnly("");
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(RequestMapping.class).method()).containsOnly(RequestMethod.GET);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(ResponseStatus.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(ResponseStatus.class).value()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotations()).hasSize(4);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(ApiOperation.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(ApiResponses.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(RequestMapping.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(RequestMapping.class).value()).containsOnly("");
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(RequestMapping.class).method()).containsOnly(RequestMethod.POST);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(ResponseStatus.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(ResponseStatus.class).value()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getParameterAnnotations()[0]).hasSize(1);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getParameterAnnotations()[0][0].annotationType()).isEqualTo(RequestBody.class);
    }

    @Test
    public void findAll() throws Exception {

        Account object1 = new Account().setName("account1");
        Account object2 = new Account().setName("account2");
        Mockito.when(accountService.findAll()).thenReturn(Lists.newArrayList(object1, object2));

        List<Account> objects = accountController.findAll();

        Assertions.assertThat(objects).hasSize(2);
        Mockito.verify(accountService).findAll();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void add() throws Exception {
        Account object = new Account().setName("my account to add");
        Mockito.when(accountService.add(Mockito.any(Account.class))).then(new Answer<Void>() {
            public Void answer(InvocationOnMock var1) throws Throwable {
                result = (Account) var1.getArguments()[0];
                return null;
            }
        });

        accountController.add(object);

        Assertions.assertThat(result).isEqualTo(object);

        Mockito.verify(accountService).add(Mockito.any(Account.class));
        Mockito.verifyNoMoreInteractions(accountService);
    }

}

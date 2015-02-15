package org.pmp.budgeto.domain.account;

import com.google.common.collect.Sets;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponses;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.pmp.budgeto.common.controller.DefaultControllerAdvice;
import org.pmp.budgeto.common.domain.DomainNotFoundException;
import org.pmp.budgeto.test.config.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private AccountDomain accountDomain;

    private AccountController accountController;

    @Before
    public void setup() {
        accountController = new AccountController(accountDomain, TestConfig.translatorTools);
    }

    @Test
    public void springConf() throws Exception {
        Assertions.assertThat(AccountController.class.getAnnotations()).hasSize(3);
        Assertions.assertThat(AccountController.class.isAnnotationPresent(RestController.class)).isTrue();
        Assertions.assertThat(AccountController.class.isAnnotationPresent(Api.class)).isTrue();
        Assertions.assertThat(AccountController.class.isAnnotationPresent(RequestMapping.class)).isTrue();
        Assertions.assertThat(AccountController.class.getAnnotation(RequestMapping.class).value()).containsOnly("account");
        Assertions.assertThat(AccountController.class.getAnnotation(RequestMapping.class).consumes()).isEmpty();
        Assertions.assertThat(AccountController.class.getAnnotation(RequestMapping.class).produces()).containsOnly(DefaultControllerAdvice.JSON_CONTENT_TYPE);

        Assertions.assertThat(AccountController.class.getConstructors()).hasSize(1);
        Assertions.assertThat(AccountController.class.getConstructors()[0].isAnnotationPresent(Autowired.class)).isTrue();

        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotations()).hasSize(4);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(ApiOperation.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(ApiResponses.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(RequestMapping.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(RequestMapping.class).consumes()).isEmpty();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(RequestMapping.class).produces()).isEmpty();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(RequestMapping.class).value()).containsOnly("");
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(RequestMapping.class).method()).containsOnly(RequestMethod.GET);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(ResponseStatus.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("findAll").getAnnotation(ResponseStatus.class).value()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotations()).hasSize(4);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(ApiOperation.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(ApiResponses.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(RequestMapping.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(RequestMapping.class).consumes()).contains(DefaultControllerAdvice.JSON_CONTENT_TYPE);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(RequestMapping.class).produces()).isEmpty();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(RequestMapping.class).value()).containsOnly("");
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(RequestMapping.class).method()).containsOnly(RequestMethod.POST);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(ResponseStatus.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getAnnotation(ResponseStatus.class).value()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getParameterAnnotations()[0]).hasSize(1);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("add", new Class[]{Account.class}).getParameterAnnotations()[0][0].annotationType()).isEqualTo(RequestBody.class);

        Assertions.assertThat(AccountController.class.getDeclaredMethod("find", String.class).getAnnotations()).hasSize(4);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("find", String.class).getAnnotation(ApiOperation.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("find", String.class).getAnnotation(ApiResponses.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("find", String.class).getAnnotation(RequestMapping.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("find", String.class).getAnnotation(RequestMapping.class).consumes()).isEmpty();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("find", String.class).getAnnotation(RequestMapping.class).produces()).isEmpty();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("find", String.class).getAnnotation(RequestMapping.class).value()).containsOnly("{name}");
        Assertions.assertThat(AccountController.class.getDeclaredMethod("find", String.class).getAnnotation(RequestMapping.class).method()).containsOnly(RequestMethod.GET);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("find", String.class).getAnnotation(ResponseStatus.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("find", String.class).getAnnotation(ResponseStatus.class).value()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(AccountController.class.getDeclaredMethod("operations", String.class).getAnnotations()).hasSize(4);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("operations", String.class).getAnnotation(ApiOperation.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("operations", String.class).getAnnotation(ApiResponses.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("operations", String.class).getAnnotation(RequestMapping.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("operations", String.class).getAnnotation(RequestMapping.class).consumes()).isEmpty();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("operations", String.class).getAnnotation(RequestMapping.class).produces()).isEmpty();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("operations", String.class).getAnnotation(RequestMapping.class).value()).containsOnly("{name}/operations");
        Assertions.assertThat(AccountController.class.getDeclaredMethod("operations", String.class).getAnnotation(RequestMapping.class).method()).containsOnly(RequestMethod.GET);
        Assertions.assertThat(AccountController.class.getDeclaredMethod("operations", String.class).getAnnotation(ResponseStatus.class)).isNotNull();
        Assertions.assertThat(AccountController.class.getDeclaredMethod("operations", String.class).getAnnotation(ResponseStatus.class).value()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void findAll() throws Exception {

        Account object1 = new Account().setName("account1");
        Account object2 = new Account().setName("account2");
        Mockito.when(accountDomain.findAll()).thenReturn(Sets.newHashSet(object1, object2));

        Set<Account> objects = accountController.findAll();

        Assertions.assertThat(objects).hasSize(2);

        Mockito.verify(accountDomain).findAll();
        Mockito.verifyNoMoreInteractions(accountDomain);
    }

    @Test
    public void findNull() throws Exception {
        expectedException.expect(DomainNotFoundException.class);
        expectedException.expectMessage("account with name accountXXX not found");

        Account object = new Account().setName("accountYYYY");
        Mockito.when(accountDomain.find(Matchers.anyString())).thenReturn(null);
        Mockito.when(accountDomain.find("accountYYYY")).thenReturn(object);

        Account account = accountController.find("accountXXX");
    }

    @Test
    public void find() throws Exception {
        Account object = new Account().setName("accountYYYY");
        Mockito.when(accountDomain.find(Matchers.anyString())).thenReturn(null);
        Mockito.when(accountDomain.find("accountYYYY")).thenReturn(object);

        Account account = accountController.find("accountYYYY");

        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getName()).isEqualTo("accountYYYY");

        Mockito.verify(accountDomain).find("accountYYYY");
        Mockito.verifyNoMoreInteractions(accountDomain);
    }

    @Test
    public void add() throws Exception {
        Account object = new Account().setName("my account to add");
        ResultExtractor extractor = new ResultExtractor();
        Mockito.when(accountDomain.add(Mockito.any(Account.class))).then(extractor);

        accountController.add(object);

        Assertions.assertThat(extractor.result).isEqualTo(object);

        Mockito.verify(accountDomain).add(Mockito.any(Account.class));
        Mockito.verifyNoMoreInteractions(accountDomain);
    }

    @Test
    public void operations() throws Exception {
        Account object = new Account().setName("accountYYYY")
                .addOperations(new Operation(TestConfig.dateTools).setLabel("ope1"))
                .addOperations(new Operation(TestConfig.dateTools).setLabel("op2"));
        Mockito.when(accountDomain.find(Matchers.anyString())).thenReturn(null);
        Mockito.when(accountDomain.find("accountYYYY")).thenReturn(object);

        Set<Operation> operations = accountController.operations("accountYYYY");

        Assertions.assertThat(operations).isNotNull();
        Assertions.assertThat(operations).hasSize(2);
        Assertions.assertThat(operations).extracting("label").contains("ope1", "op2");

        Mockito.verify(accountDomain).find("accountYYYY");
        Mockito.verifyNoMoreInteractions(accountDomain);
    }

    private static class ResultExtractor implements Answer<Void> {

        Account result;

        @Override
        public Void answer(InvocationOnMock var1) throws Throwable {
            result = (Account) var1.getArguments()[0];
            return null;
        }
    }

}

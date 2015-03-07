package org.pmp.budgeto.common.controller;

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
import org.pmp.budgeto.common.domain.DomainNotFoundException;
import org.pmp.budgeto.domain.account.Account;
import org.pmp.budgeto.domain.account.AccountController;
import org.pmp.budgeto.domain.account.AccountDomain;
import org.pmp.budgeto.domain.account.Operation;
import org.pmp.budgeto.test.config.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Set;


@RunWith(MockitoJUnitRunner.class)
public class ApiControllerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ApiController apiController;

    @Before
    public void setup() {
        apiController = new ApiController();
    }

    @Test
    public void springConf() throws Exception {

        Class<?> clazz = apiController.getClass();
        Assertions.assertThat(clazz.getAnnotations()).hasSize(3);
        Assertions.assertThat(clazz.isAnnotationPresent(RestController.class)).isTrue();
        Assertions.assertThat(clazz.isAnnotationPresent(Api.class)).isTrue();
        Assertions.assertThat(clazz.isAnnotationPresent(RequestMapping.class)).isTrue();
        Assertions.assertThat(clazz.getAnnotation(RequestMapping.class).value()).containsOnly("");
        Assertions.assertThat(clazz.getAnnotation(RequestMapping.class).consumes()).isEmpty();
        Assertions.assertThat(clazz.getAnnotation(RequestMapping.class).produces()).containsOnly(DefaultControllerAdvice.JSON_CONTENT_TYPE);

        Assertions.assertThat(clazz.getConstructors()).hasSize(1);
//        Assertions.assertThat(clazz.getConstructors()[0].isAnnotationPresent(Autowired.class)).isTrue();

        Method mGet = clazz.getDeclaredMethod("get");
        Assertions.assertThat(mGet.getAnnotations()).hasSize(4);
        Assertions.assertThat(mGet.getAnnotation(ApiOperation.class)).isNotNull();
        Assertions.assertThat(mGet.getAnnotation(ApiResponses.class)).isNotNull();
        Assertions.assertThat(mGet.getAnnotation(RequestMapping.class)).isNotNull();
        Assertions.assertThat(mGet.getAnnotation(RequestMapping.class).consumes()).isEmpty();
        Assertions.assertThat(mGet.getAnnotation(RequestMapping.class).produces()).isEmpty();
        Assertions.assertThat(mGet.getAnnotation(RequestMapping.class).value()).containsOnly("");
        Assertions.assertThat(mGet.getAnnotation(RequestMapping.class).method()).containsOnly(RequestMethod.GET);
        Assertions.assertThat(mGet.getAnnotation(ResponseStatus.class)).isNotNull();
        Assertions.assertThat(mGet.getAnnotation(ResponseStatus.class).value()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void get() throws Exception {
        org.pmp.budgeto.common.controller.Api object = apiController.get();

        Assertions.assertThat(object).isNotNull();
    }

}

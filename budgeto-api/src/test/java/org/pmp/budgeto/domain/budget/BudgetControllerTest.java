package org.pmp.budgeto.domain.budget;

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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RunWith(MockitoJUnitRunner.class)
public class BudgetControllerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private BudgetDomain budgetDomain;

    private BudgetController budgetController;

    @Before
    public void setup() {
        budgetController = new BudgetController(budgetDomain);
    }

    @Test
    public void springConf() throws Exception {
        Assertions.assertThat(BudgetController.class.getAnnotations()).hasSize(3);
        Assertions.assertThat(BudgetController.class.isAnnotationPresent(RestController.class)).isTrue();
        Assertions.assertThat(BudgetController.class.isAnnotationPresent(Api.class)).isTrue();
        Assertions.assertThat(BudgetController.class.isAnnotationPresent(RequestMapping.class)).isTrue();
        Assertions.assertThat(BudgetController.class.getAnnotation(RequestMapping.class).value()).containsOnly("budget");

        Assertions.assertThat(BudgetController.class.getConstructors()).hasSize(1);
        Assertions.assertThat(BudgetController.class.getConstructors()[0].isAnnotationPresent(Autowired.class)).isTrue();

        Assertions.assertThat(BudgetController.class.getDeclaredMethod("findAll").getAnnotations()).hasSize(4);
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("findAll").getAnnotation(ApiOperation.class)).isNotNull();
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("findAll").getAnnotation(ApiResponses.class)).isNotNull();
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("findAll").getAnnotation(RequestMapping.class)).isNotNull();
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("findAll").getAnnotation(RequestMapping.class).value()).containsOnly("");
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("findAll").getAnnotation(RequestMapping.class).method()).containsOnly(RequestMethod.GET);
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("findAll").getAnnotation(ResponseStatus.class)).isNotNull();
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("findAll").getAnnotation(ResponseStatus.class).value()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(BudgetController.class.getDeclaredMethod("add", new Class[]{Budget.class}).getAnnotations()).hasSize(4);
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("add", new Class[]{Budget.class}).getAnnotation(ApiOperation.class)).isNotNull();
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("add", new Class[]{Budget.class}).getAnnotation(ApiResponses.class)).isNotNull();
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("add", new Class[]{Budget.class}).getAnnotation(RequestMapping.class)).isNotNull();
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("add", new Class[]{Budget.class}).getAnnotation(RequestMapping.class).value()).containsOnly("");
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("add", new Class[]{Budget.class}).getAnnotation(RequestMapping.class).method()).containsOnly(RequestMethod.POST);
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("add", new Class[]{Budget.class}).getAnnotation(ResponseStatus.class)).isNotNull();
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("add", new Class[]{Budget.class}).getAnnotation(ResponseStatus.class).value()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("add", new Class[]{Budget.class}).getParameterAnnotations()[0]).hasSize(1);
        Assertions.assertThat(BudgetController.class.getDeclaredMethod("add", new Class[]{Budget.class}).getParameterAnnotations()[0][0].annotationType()).isEqualTo(RequestBody.class);
    }

    @Test
    public void findAll() throws Exception {

        Budget object1 = new Budget().setName("budget1");
        Budget object2 = new Budget().setName("budget2");
        Mockito.when(budgetDomain.findAll()).thenReturn(Sets.newHashSet(object1, object2));

        Set<Budget> objects = budgetController.findAll();

        Assertions.assertThat(objects).hasSize(2);
        Mockito.verify(budgetDomain).findAll();
        Mockito.verifyNoMoreInteractions(budgetDomain);
    }

    @Test
    public void add() throws Exception {
        Budget object = new Budget().setName("my budget to add");
        ResultExtractor extractor = new ResultExtractor();
        Mockito.when(budgetDomain.add(Mockito.any(Budget.class))).then(extractor);

        budgetController.add(object);

        Assertions.assertThat(extractor.result).isEqualTo(object);

        Mockito.verify(budgetDomain).add(Mockito.any(Budget.class));
        Mockito.verifyNoMoreInteractions(budgetDomain);
    }

    private static class ResultExtractor implements Answer<Void> {

        Budget result;

        @Override
        public Void answer(InvocationOnMock var1) throws Throwable {
            result = (Budget) var1.getArguments()[0];
            return null;
        }
    }

}

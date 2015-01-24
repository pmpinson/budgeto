package org.pmp.budgeto.domain.budget;

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
public class BudgetControllerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private BudgetService budgetService;

    private BudgetController budgetController;

    private Budget result;

    @Before
    public void setup() {
        budgetController = new BudgetController(budgetService);
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
        Mockito.when(budgetService.findAll()).thenReturn(Lists.newArrayList(object1, object2));

        List<Budget> objects = budgetController.findAll();

        Assertions.assertThat(objects).hasSize(2);
        Mockito.verify(budgetService).findAll();
        Mockito.verifyNoMoreInteractions(budgetService);
    }

    @Test
    public void add() throws Exception {
        Budget object = new Budget().setName("my budget to add");
        Mockito.when(budgetService.add(Mockito.any(Budget.class))).then(new Answer<Void>() {
            public Void answer(InvocationOnMock var1) throws Throwable {
                result = (Budget) var1.getArguments()[0];
                return null;
            }
        });

        budgetController.add(object);

        Assertions.assertThat(result).isEqualTo(object);

        Mockito.verify(budgetService).add(Mockito.any(Budget.class));
        Mockito.verifyNoMoreInteractions(budgetService);
    }
//
//    @Test
//    public void addObjectNull() throws Exception {
//
//        expectedException.expect(NullPointerException.class);
//        expectedException.expectMessage("Objet vide budget");
//
//        budgetService.add(null);
//
//        Mockito.verify(budgetRepository).save(Mockito.any(Budget.class));
//        Mockito.verifyNoMoreInteractions(budgetRepository);
//    }
//
//    @Test
//    public void addWithValidationError() throws Exception {
//
//        Budget object = new Budget();
//
//        expectedException.expect(ServiceException.class);
//        expectedException.expectMessage("Erreur lors de la validation de budget");
//
//        budgetService.add(object);
//
//        Mockito.verify(budgetRepository).save(Mockito.any(Budget.class));
//        Mockito.verifyNoMoreInteractions(budgetRepository);
//    }
//
//    @Test
//    public void addUnknownDataAccessException() throws Exception {
//
//        Budget object = new Budget();
//        object.setName("my budget to add");
//        DataAccessException ex = Mockito.mock(DataAccessException.class);
//        Mockito.when(budgetRepository.save(Mockito.any(Budget.class))).thenThrow(ex);
//
//        expectedException.expect(DataAccessException.class);
//
//        budgetService.add(object);
//
//        Mockito.verify(budgetRepository).save(Mockito.any(Budget.class));
//        Mockito.verifyNoMoreInteractions(budgetRepository);
//    }
//
//    @Test
//    public void addDataAccessException() throws Exception {
//
//        Budget object = new Budget();
//        object.setName("my budget to add");
//        DataAccessException ex = Mockito.mock(DataAccessException.class);
//        Mockito.when(budgetRepository.save(Mockito.any(Budget.class))).thenThrow(ex);
//        DuplicateKeyException dupEx = Mockito.mock(DuplicateKeyException.class);
//        Mockito.when(ex.getCause()).thenReturn(dupEx);
//        Mockito.when(ex.getMessage()).thenReturn("error on budgetUniqueName");
//
//        expectedException.expect(ServiceValidationException.class);
//
//        budgetService.add(object);
//
//        Mockito.verify(budgetRepository).save(Mockito.any(Budget.class));
//        Mockito.verifyNoMoreInteractions(budgetRepository);
//    }

}

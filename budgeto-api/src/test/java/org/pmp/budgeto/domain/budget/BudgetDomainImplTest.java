package org.pmp.budgeto.domain.budget;

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
import org.pmp.budgeto.test.config.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Set;


@RunWith(MockitoJUnitRunner.class)
public class BudgetDomainImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private BudgetRepository budgetRepository;

    private BudgetDomain budgetDomain;

    @Before
    public void setup() {
        budgetDomain = new BudgetDomainImpl(budgetRepository, TestConfig.translatorTools, TestConfig.domainTools);
    }

    @Test
    public void springConf() throws Exception {
        Assertions.assertThat(BudgetDomainImpl.class.getAnnotations()).hasSize(1);
        Assertions.assertThat(BudgetDomainImpl.class.isAnnotationPresent(Service.class)).isTrue();

        Assertions.assertThat(BudgetDomainImpl.class.getConstructors()).hasSize(1);
        Assertions.assertThat(BudgetDomainImpl.class.getConstructors()[0].isAnnotationPresent(Autowired.class)).isTrue();
    }

    @Test
    public void findAll() throws Exception {

        Budget object1 = new Budget().setName("budget1");
        Budget object2 = new Budget().setName("budget2");
        Mockito.when(budgetRepository.findAll()).thenReturn(Lists.newArrayList(object1, object2));

        Set<Budget> objects = budgetDomain.findAll();

        Assertions.assertThat(objects).hasSize(2);
        Mockito.verify(budgetRepository).findAll();
        Mockito.verifyNoMoreInteractions(budgetRepository);
    }

    @Test
    public void add() throws Exception {

        Budget object = new Budget().setName("my budget to add");
        Mockito.when(budgetRepository.save(Mockito.any(Budget.class))).thenReturn(object);

        budgetDomain.add(object);

        Mockito.verify(budgetRepository).save(Mockito.any(Budget.class));
        Mockito.verifyNoMoreInteractions(budgetRepository);
    }

    @Test
    public void addObjectNull() throws Exception {

        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("empty object budget");

        budgetDomain.add(null);

        Mockito.verify(budgetRepository).save(Mockito.any(Budget.class));
        Mockito.verifyNoMoreInteractions(budgetRepository);
    }

    @Test
    public void addWithValidationError() throws Exception {

        Budget object = new Budget();

        expectedException.expect(DomainException.class);
        expectedException.expectMessage("object budget not valid");

        budgetDomain.add(object);

        Mockito.verify(budgetRepository).save(Mockito.any(Budget.class));
        Mockito.verifyNoMoreInteractions(budgetRepository);
    }

    @Test
    public void addUnknownDataAccessException() throws Exception {

        Budget object = new Budget().setName("my budget to add");
        DataAccessException ex = Mockito.mock(DataAccessException.class);
        Mockito.when(budgetRepository.save(Mockito.any(Budget.class))).thenThrow(ex);

        expectedException.expect(DataAccessException.class);

        budgetDomain.add(object);

        Mockito.verify(budgetRepository).save(Mockito.any(Budget.class));
        Mockito.verifyNoMoreInteractions(budgetRepository);
    }

    @Test
    public void addDataAccessException() throws Exception {

        Budget object = new Budget().setName("my budget to add");
        DataAccessException ex = Mockito.mock(DataAccessException.class);
        Mockito.when(budgetRepository.save(Mockito.any(Budget.class))).thenThrow(ex);
        DuplicateKeyException dupEx = Mockito.mock(DuplicateKeyException.class);
        Mockito.when(ex.getCause()).thenReturn(dupEx);
        Mockito.when(ex.getMessage()).thenReturn("error on budgetUniqueName");

        expectedException.expect(DomainConflictException.class);

        budgetDomain.add(object);

        Mockito.verify(budgetRepository).save(Mockito.any(Budget.class));
        Mockito.verifyNoMoreInteractions(budgetRepository);
    }

}

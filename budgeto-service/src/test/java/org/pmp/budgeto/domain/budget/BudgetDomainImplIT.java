package org.pmp.budgeto.domain.budget;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.pmp.budgeto.common.domain.DomainConflictException;
import org.pmp.budgeto.common.domain.DomainException;
import org.pmp.budgeto.test.config.ITConfig;
import org.pmp.budgeto.test.matcher.DomainExceptionValidationErrorContentMatcher;
import org.pmp.budgeto.test.matcher.DomainExceptionValidationErrorsSizeMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class, BudgetConfig.class})
@ActiveProfiles("test")
public class BudgetDomainImplIT {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private BudgetHelper budgetHelper;

    @Autowired
    private BudgetDomain budgetDomain;

    @Before
    public void setup() throws Exception {

        budgetHelper.init();
    }

    @Test
    public void findAll() throws Exception {

        List<Budget> objects = budgetDomain.findAll();

        Assertions.assertThat(objects).hasSize(2);
        budgetHelper.controlBudget1(budgetHelper.findByName(objects, "budget1"));
        budgetHelper.controlBudget2(budgetHelper.findByName(objects, "budget2"));
    }

    @Test
    public void add() throws Exception {

        Budget object = new Budget().setName("my budget to add");

        Budget newObject = budgetDomain.add(object);

        Assertions.assertThat(budgetHelper.nbBudgets()).isEqualTo(3);
        Assertions.assertThat(newObject.getName()).isEqualTo("my budget to add");
    }

    @Test
    public void addWithValidationError() throws Exception {

        Budget object = new Budget();

        expectedException.expect(DomainException.class);
        expectedException.expectMessage("object budget not valid");

        budgetDomain.add(object);
    }

    @Test
    public void addWithSameName() throws Exception {

        Budget object = new Budget().setName("budget2");

        expectedException.expect(DomainConflictException.class);
        expectedException.expectMessage("error on a constraint during add of budget");
        expectedException.expect(new DomainExceptionValidationErrorsSizeMatcher(1));
        expectedException.expect(new DomainExceptionValidationErrorContentMatcher("name", new String[]{"a budget (budget2) already exist with same name"}));

        budgetDomain.add(object);
    }

}

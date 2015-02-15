package org.pmp.budgeto.domain.account;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.joda.time.DateTime;
import org.junit.Test;
import org.pmp.budgeto.test.config.TestConfig;
import org.pmp.budgeto.test.extractor.ConstraintViolationExtractor;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


public class AccountTest {

    @Test
    public void getterSetter() throws Exception {

        Account object = new Account().setName("theName").setNote("theNote");

        Assertions.assertThat(object.getName()).isEqualTo("theName");
        Assertions.assertThat(object.getName()).isEqualTo(object.getName());
        Assertions.assertThat(object.getNote()).isEqualTo("theNote");
        Assertions.assertThat(object.getNote()).isEqualTo(object.getNote());
    }

    @Test
    public void equalsAndHashcodeDefault() throws Exception {

        Account object1 = new Account().setName("theName");
        Account object2 = new Account().setName("theName");

        Assertions.assertThat(object1).isEqualTo(object2);
        Assertions.assertThat(object1.hashCode()).isEqualTo(object2.hashCode());
    }

    @Test
    public void equalsAndHashcodeDifNote() throws Exception {

        Account object1 = new Account().setName("theName").setNote("theNote1");
        Account object2 = new Account().setName("theName").setNote("theNote2");

        Assertions.assertThat(object1).isEqualTo(object2);
        Assertions.assertThat(object1.hashCode()).isEqualTo(object2.hashCode());
    }

    @Test
    public void equalsSame() throws Exception {

        Account object1 = new Account().setName("theName");

        Assertions.assertThat(object1).isEqualTo(object1);
    }

    @Test
    public void notEqualsAndHashcodeDefault() throws Exception {

        Account object1 = new Account().setName("theName");
        Account object2 = new Account().setName("theName2");

        Assertions.assertThat(object1).isNotEqualTo(object2);
        Assertions.assertThat(object1.hashCode()).isNotEqualTo(object2.hashCode());
    }

    @Test
    public void notEqualsToNull() throws Exception {

        Account object1 = new Account().setName("theName");
        Account object2 = null;

        Assertions.assertThat(object1).isNotEqualTo(object2);
    }

    @Test
    public void notEqualsOtherClass() throws Exception {

        Account object1 = new Account().setName("theName");

        Assertions.assertThat(object1).isNotEqualTo("test string");
    }

    @Test
    public void toStringDefault() throws Exception {

        Account object = new Account().setName("theName").setNote("theNote");

        Assertions.assertThat(object.toString()).isEqualTo("Account[name=theName,note=theNote,operations=[],links=[]]");
    }

    @Test
    public void validationValid() throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Account object = new Account().setName("theName").setNote("theNote");

        Set<ConstraintViolation<Account>> violations = validator.validate(object);

        Assertions.assertThat(violations).hasSize(0);
    }

    @Test
    public void validationNoName() throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Account object = new Account().setName("   ").setNote("theNote");

        Set<ConstraintViolation<Account>> violations = validator.validate(object);

        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations).extracting(new ConstraintViolationExtractor()).contains(
                Tuple.tuple("name", "must not empty string (trim too)"));
    }

    @Test
    public void operations() throws Exception {

        Operation operation1 = new Operation(TestConfig.dateTools).setDate(DateTime.now()).setLabel("ope1");
        Operation operation2 = new Operation(TestConfig.dateTools).setDate(DateTime.now().minusDays(3)).setLabel("ope2");
        Account object = new Account().setName("theName").addOperations(operation1).addOperations(operation2);

        Assertions.assertThat(object.getOperations()).hasSize(2);
        Assertions.assertThat(object.getOperations()).contains(operation1, operation2);
    }

    @Test
    public void validateOperationsGood() throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Operation operation1 = new Operation(TestConfig.dateTools).setDate(DateTime.now()).setLabel("ope1");
        Operation operation2 = new Operation(TestConfig.dateTools).setDate(DateTime.now().minusDays(3)).setLabel("ope2");
        Account object = new Account().setName("theName").addOperations(operation1).addOperations(operation2);

        Set<ConstraintViolation<Account>> violations = validator.validate(object);

        Assertions.assertThat(violations).hasSize(0);
    }

    @Test
    public void validateOperationsError() throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Operation operation1 = new Operation(TestConfig.dateTools).setDate(DateTime.now()).setLabel("ope1");
        Operation operation2 = new Operation(TestConfig.dateTools).setLabel("ope2");
        Account object = new Account().setName("theName").addOperations(operation1).addOperations(operation2);

        Set<ConstraintViolation<Account>> violations = validator.validate(object);

        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(((ConstraintViolation<Account>) violations.toArray()[0]).getMessageTemplate()).isEqualTo("{javax.validation.constraints.NotNull.message}");
        Assertions.assertThat(((ConstraintViolation<Account>) violations.toArray()[0]).getPropertyPath().toString()).isEqualTo("operations[].date");
    }

}

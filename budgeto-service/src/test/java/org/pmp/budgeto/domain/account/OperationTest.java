package org.pmp.budgeto.domain.account;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.pmp.budgeto.common.tools.DateTools;
import org.pmp.budgeto.test.AssertTools;
import org.pmp.budgeto.test.TestTools;
import org.pmp.budgeto.test.config.TestConfig;
import org.pmp.budgeto.test.extractor.ConstraintViolationExtractor;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


public class OperationTest {

    @Before
    public void setup() {
        TestConfig.init();
    }

    @Test
    public void constructors() throws Exception {
        AssertTools.assertThat(Operation.class).hasConstructors(2);
        AssertTools.assertThat(Operation.class.getDeclaredConstructor(new Class[]{})).isPrivate();
        AssertTools.assertThat(Operation.class.getDeclaredConstructor(new Class[]{DateTools.class})).isPublic();

        TestTools.callPrivateConstructor(Operation.class);
    }

    @Test
    public void getterSetter() throws Exception {

        DateTime date = TestConfig.dateTools.getFormatterDate().parseDateTime("2015-05-08");
        Operation object = new Operation(TestConfig.dateTools).setDate(date).setLabel("theLabel");

        Assertions.assertThat(object.getLabel()).isEqualTo("theLabel");
        Assertions.assertThat(object.getLabel()).isEqualTo(object.getLabel());
        Assertions.assertThat(object.getDate()).isEqualTo(TestConfig.dateTools.toUTCDate(date));
        Assertions.assertThat(object.getDate()).isEqualTo(object.getDate());
    }

    @Test
    public void getterSetterDateString() throws Exception {

        DateTime date = TestConfig.dateTools.getFormatterDate().parseDateTime("2015-05-08");
        Operation object = new Operation(TestConfig.dateTools).setDateString("2015-05-08").setLabel("theLabel");

        Assertions.assertThat(object.getLabel()).isEqualTo("theLabel");
        Assertions.assertThat(object.getLabel()).isEqualTo(object.getLabel());
        Assertions.assertThat(object.getDate()).isEqualTo(TestConfig.dateTools.toUTCDate(date));
        Assertions.assertThat(object.getDate()).isEqualTo(object.getDate());
    }

    @Test
    public void notEqualsAndHashcodeDefault() throws Exception {

        Operation object1 = new Operation(TestConfig.dateTools).setLabel("theLabel");
        Operation object2 = new Operation(TestConfig.dateTools).setLabel("theLabel");

        Assertions.assertThat(object1).isNotEqualTo(object2);
        Assertions.assertThat(object1.hashCode()).isNotEqualTo(object2.hashCode());
    }

    @Test
    public void equalsSame() throws Exception {

        Operation object1 = new Operation(TestConfig.dateTools).setLabel("theLabel");

        Assertions.assertThat(object1).isEqualTo(object1);
    }

    @Test
    public void notEqualsToNull() throws Exception {

        Operation object1 = new Operation(TestConfig.dateTools).setLabel("theLabel");
        Operation object2 = null;

        Assertions.assertThat(object1).isNotEqualTo(object2);
    }

    @Test
    public void notEqualsOtherClass() throws Exception {

        Operation object1 = new Operation(TestConfig.dateTools).setLabel("theLabel");

        Assertions.assertThat(object1).isNotEqualTo("test string");
    }

    @Test
    public void toStringDefault() throws Exception {

        Operation object = new Operation(TestConfig.dateTools).setLabel("theName").setDateString("2015-01-18");

        Assertions.assertThat(object.toString()).isEqualTo("Operation[label=theName,date=2015-01-18T00:00:00+0000]");
    }

    @Test
    public void validationValid() throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Operation object = new Operation(TestConfig.dateTools).setLabel("theName").setDate(DateTime.now());

        Set<ConstraintViolation<Operation>> violations = validator.validate(object);

        Assertions.assertThat(violations).hasSize(0);
    }

    @Test
    public void validationNoLabel() throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Operation object = new Operation(TestConfig.dateTools).setDate(DateTime.now());

        Set<ConstraintViolation<Operation>> violations = validator.validate(object);

        Assertions.assertThat(violations).hasSize(2);
        Assertions.assertThat(violations).extracting(new ConstraintViolationExtractor()).contains(
                Tuple.tuple("label", "must not empty string (trim too)")
                , Tuple.tuple("label", "may not be null"));
    }

    @Test
    public void validationNoDat() throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Operation object = new Operation(TestConfig.dateTools).setLabel("theName");

        Set<ConstraintViolation<Operation>> violations = validator.validate(object);

        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(((ConstraintViolation<Account>) violations.toArray()[0]).getMessageTemplate()).isEqualTo("{javax.validation.constraints.NotNull.message}");
        Assertions.assertThat(((ConstraintViolation<Account>) violations.toArray()[0]).getPropertyPath().toString()).isEqualTo("date");
    }

}

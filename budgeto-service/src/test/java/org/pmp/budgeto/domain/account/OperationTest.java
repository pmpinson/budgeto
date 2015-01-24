package org.pmp.budgeto.domain.account;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.pmp.budgeto.common.tools.DateTools;
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
    public void getterSetter() throws Exception {

        DateTime now = DateTime.now();
        Operation object = new Operation().setDate(now).setLabel("theLabel");

        Assertions.assertThat(object.getLabel()).isEqualTo("theLabel");
        Assertions.assertThat(object.getLabel()).isEqualTo(object.getLabel());
        Assertions.assertThat(object.getDate()).isEqualTo(DateTools.toUTC(DateTools.truncateTime(now)));
        Assertions.assertThat(object.getDate()).isEqualTo(object.getDate());
    }

    @Test
    public void notEqualsAndHashcodeDefault() throws Exception {

        Operation object1 = new Operation().setLabel("theLabel");
        Operation object2 = new Operation().setLabel("theLabel");

        Assertions.assertThat(object1).isNotEqualTo(object2);
        Assertions.assertThat(object1.hashCode()).isNotEqualTo(object2.hashCode());
    }

    @Test
    public void equalsSame() throws Exception {

        Operation object1 = new Operation().setLabel("theLabel");

        Assertions.assertThat(object1).isEqualTo(object1);
    }

    @Test
    public void notEqualsToNull() throws Exception {

        Operation object1 = new Operation().setLabel("theLabel");
        Operation object2 = null;

        Assertions.assertThat(object1).isNotEqualTo(object2);
    }

    @Test
    public void notEqualsOtherClass() throws Exception {

        Operation object1 = new Operation().setLabel("theLabel");

        Assertions.assertThat(object1).isNotEqualTo("test string");
    }

    @Test
    public void toStringDefault() throws Exception {

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss ZZZ");

        Operation object = new Operation().setLabel("theName").setDate(formatter.parseDateTime("2015/01/18 18:52:26 America/Los_Angeles"));

        Assertions.assertThat(object.toString()).isEqualTo("Operation[date=2015-01-18T00:00:00.000Z,label=theName]");
    }

    @Test
    public void validationValid() throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Operation object = new Operation().setLabel("theName").setDate(DateTime.now());

        Set<ConstraintViolation<Operation>> violations = validator.validate(object);

        Assertions.assertThat(violations).hasSize(0);
    }

    @Test
    public void validationNoLabel() throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Operation object = new Operation().setDate(DateTime.now());

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
        Operation object = new Operation().setLabel("theName");

        Set<ConstraintViolation<Operation>> violations = validator.validate(object);

        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(((ConstraintViolation<Account>) violations.toArray()[0]).getMessageTemplate()).isEqualTo("{javax.validation.constraints.NotNull.message}");
        Assertions.assertThat(((ConstraintViolation<Account>) violations.toArray()[0]).getPropertyPath().toString()).isEqualTo("date");
    }

}

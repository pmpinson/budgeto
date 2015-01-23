package org.pmp.budgeto.common.tools;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.common.domain.validator.TrimNotEmpty;
import org.pmp.budgeto.test.config.TestConfig;
import org.pmp.budgeto.test.extractor.ConstraintViolationExtractor;

import javax.validation.ConstraintViolation;
import java.util.Set;


@RunWith(MockitoJUnitRunner.class)
public class TrimNotEmptyValidatorTest {

    @Before
    public void setup() {
        TestConfig.init();
    }

    @Test
    public void validateNoError() throws Exception {
        MyObjectToValidate object = new MyObjectToValidate("the value for name");

        Set<ConstraintViolation<MyObjectToValidate>> violations = TestConfig.validatorTools.validate(object);

        Assertions.assertThat(violations).isNotNull();
        Assertions.assertThat(violations).hasSize(0);
    }

    @Test
    public void validateWithNullValue() throws Exception {
        MyObjectToValidate object = new MyObjectToValidate(null);

        Set<ConstraintViolation<MyObjectToValidate>> violations = TestConfig.validatorTools.validate(object);

        Assertions.assertThat(violations).isNotNull();
        Assertions.assertThat(violations).hasSize(2);
        Assertions.assertThat(violations).extracting(new ConstraintViolationExtractor()).contains(
                Assertions.tuple("name", "must not empty string (trim too)"), Assertions.tuple("name", "may not be null"));
    }

    @Test
    public void validateWithEmptyValue() throws Exception {
        MyObjectToValidate object = new MyObjectToValidate("");

        Set<ConstraintViolation<MyObjectToValidate>> violations = TestConfig.validatorTools.validate(object);

        Assertions.assertThat(violations).isNotNull();
        Assertions.assertThat(violations).hasSize(1);

        Assertions.assertThat(violations).extracting(new ConstraintViolationExtractor()).containsOnly(
                Assertions.tuple("name", "must not empty string (trim too)"));
    }

    @Test
    public void validateWithSpaceValue() throws Exception {
        MyObjectToValidate object = new MyObjectToValidate("   ");

        Set<ConstraintViolation<MyObjectToValidate>> violations = TestConfig.validatorTools.validate(object);

        Assertions.assertThat(violations).isNotNull();
        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations).extracting(new ConstraintViolationExtractor()).containsOnly(
                Assertions.tuple("name", "must not empty string (trim too)"));
    }

    public class MyObjectToValidate {

        @TrimNotEmpty
        private String name = null;

        public MyObjectToValidate(String name) {
            this.name = name;
        }

    }

}

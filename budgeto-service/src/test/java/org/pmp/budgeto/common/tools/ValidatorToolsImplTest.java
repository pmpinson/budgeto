package org.pmp.budgeto.common.tools;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.common.domain.validator.TrimNotEmpty;
import org.pmp.budgeto.test.config.TestConfig;
import org.pmp.budgeto.test.extractor.ConstraintViolationExtractor;
import org.springframework.stereotype.Component;

import javax.validation.*;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;
import java.util.Set;


@RunWith(MockitoJUnitRunner.class)
public class ValidatorToolsImplTest {

    @Before
    public void setup() {
        TestConfig.init();
    }

    @Test
    public void springConf() throws Exception {
        Assertions.assertThat(ValidatorToolsImpl.class.getAnnotations()).hasSize(1);
        Assertions.assertThat(ValidatorToolsImpl.class.isAnnotationPresent(Component.class)).isTrue();
    }

    @Test
    public void validateNoError() throws Exception {
        MyObjectToValidate object = new MyObjectToValidate("the value for name");

        Set<ConstraintViolation<MyObjectToValidate>> violations = TestConfig.validatorTools.validate(object);

        Assertions.assertThat(violations).isNotNull();
        Assertions.assertThat(violations).hasSize(0);
    }

    @Test
    public void validateWithError() throws Exception {
        MyObjectToValidate object = new MyObjectToValidate(null);
        object.test = true;

        Set<ConstraintViolation<MyObjectToValidate>> violations = TestConfig.validatorTools.validate(object);

        Assertions.assertThat(violations).isNotNull();
        Assertions.assertThat(violations).hasSize(4);
        Assertions.assertThat(violations).extracting(new ConstraintViolationExtractor()).containsOnly(
                Assertions.tuple("name", "must not empty string (trim too)"), Assertions.tuple("name", "may not be null")
                , Assertions.tuple("test", "must be false"), Assertions.tuple("", "object name must be not null and equals to 'the value for name'"));
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = CustomValidator.class)
    @Documented
    public static @interface CustomValidation {

        String message() default "object name must be not null and equals to 'the value for name'";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }

    public static class CustomValidator implements ConstraintValidator<CustomValidation, MyObjectToValidate> {

        /**
         * {@inheritDoc}
         */
        @Override
        public void initialize(CustomValidation constraintAnnotation) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(MyObjectToValidate value, ConstraintValidatorContext constraintValidatorContext) {
            if (value.name == null || !value.name.equals("the value for name")) {
                return false;
            }
            return true;
        }
    }

    @CustomValidation
    public class MyObjectToValidate {

        @TrimNotEmpty
        private String name = null;

        @NotNull
        @AssertFalse
        private Boolean test = false;

        public MyObjectToValidate(String name) {
            this.name = name;
        }

    }

}

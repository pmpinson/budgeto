package org.pmp.budgeto.common.domain;

import com.mongodb.DuplicateKeyException;
import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.api.Assertions;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.common.tools.TranslatorTools;
import org.pmp.budgeto.common.tools.ValidatorToolsImpl;
import org.pmp.budgeto.test.extractor.DomainValidationErrorExtractor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import javax.validation.*;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;
import java.util.Arrays;
import java.util.Set;


@RunWith(MockitoJUnitRunner.class)
public class DomainToolsImplTest {

    @Mock
    private ValidatorToolsImpl validatorTools;

    @Mock
    private TranslatorTools translatorTools;

    @InjectMocks
    private DomainToolsImpl serviceTools;

    @Test
    public void springConf() throws Exception {
        Assertions.assertThat(ValidatorToolsImpl.class.getAnnotations()).hasSize(1);
        Assertions.assertThat(ValidatorToolsImpl.class.isAnnotationPresent(Component.class)).isTrue();
    }

    @Test
    public void validateNoError() throws Exception {
        MyObjectToValidate object = new MyObjectToValidate("the value for name");

        serviceTools.validate(object, "prop.name");
    }

    @Test
    public void validateObjectNull() throws Exception {

        Mockito.when(translatorTools.get("object.null", "myobject")).thenReturn("the message null");
        Mockito.when(translatorTools.get("object.desc")).thenReturn("myobject");

        try {
            serviceTools.validate(null, "object.desc");
            Assert.fail("Exception must be thrown");
        } catch (NullPointerException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("the message null");
        }
        Mockito.verify(translatorTools).get("object.desc");
        Mockito.verify(translatorTools).get("object.null", "myobject");
        Mockito.verifyNoMoreInteractions(translatorTools, validatorTools);
    }

    @Test
    public void validateWithValidationError() throws Exception {
        MyObjectToValidate object = new MyObjectToValidate(null);
        object.test = true;

        ValidatorToolsImpl tmpValidatorTools = new ValidatorToolsImpl();
        Set<ConstraintViolation<MyObjectToValidate>> violations = tmpValidatorTools.validate(object);

        Mockito.when(translatorTools.get("object.null", "myobject")).thenReturn("the message null");
        Mockito.when(translatorTools.get("object.not.valid", "myobject")).thenReturn("the message not valid");
        Mockito.when(translatorTools.get("object.desc")).thenReturn("myobject");
        Mockito.when(validatorTools.validate(object)).thenReturn(violations);

        try {
            serviceTools.validate(object, "object.desc");
            Assert.fail("Exception must be thrown");
        } catch (DomainValidationException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("the message not valid");

            Assertions.assertThat(e.getConstraintViolations()).isNotNull();
            Assertions.assertThat(e.getConstraintViolations()).hasSize(3);

            Assertions.assertThat(e.getConstraintViolations()).extracting(new DomainValidationErrorExtractor()).containsOnly(
                    Assertions.tuple("test", Arrays.toString(ArrayUtils.toArray("doit être faux"))), Assertions.tuple("name", Arrays.toString(ArrayUtils.toArray("ne peut pas être nul", "ne peut pas être vide")))
                    , Assertions.tuple("_all", Arrays.toString(ArrayUtils.toArray("object name must be not null and equals to 'the value for name'")))
            );
        }
        Mockito.verify(translatorTools, Mockito.times(2)).get("object.desc");
        Mockito.verify(translatorTools).get("object.null", "myobject");
        Mockito.verify(translatorTools).get("object.not.valid", "myobject");
        Mockito.verify(validatorTools).validate(object);
        Mockito.verifyNoMoreInteractions(translatorTools, validatorTools);
    }

    @Test
    public void isConstraintViolationExceptionForDataNull() throws Exception {
        boolean res = serviceTools.isConstraintViolationExceptionFor(null, null);

        Assertions.assertThat(res).isEqualTo(false);
    }

    @Test
    public void isConstraintViolationExceptionForCauseNull() throws Exception {

        DataAccessException ex = Mockito.mock(DataAccessException.class);

        boolean res = serviceTools.isConstraintViolationExceptionFor(ex, null);

        Assertions.assertThat(res).isEqualTo(false);
    }

    @Test
    public void isConstraintViolationExceptionForCauseBadClass() throws Exception {

        DataAccessException ex = Mockito.mock(DataAccessException.class);
        NullPointerException dupEx = Mockito.mock(NullPointerException.class);
        Mockito.when(ex.getCause()).thenReturn(dupEx);
//        Mockito.when(ex.getMessage()).thenReturn("error on budgetUniqueName");

        boolean res = serviceTools.isConstraintViolationExceptionFor(ex, null);

        Assertions.assertThat(res).isEqualTo(false);
    }

    @Test
    public void isConstraintViolationExceptionForNullConstraint() throws Exception {

        DataAccessException ex = Mockito.mock(DataAccessException.class);
        DuplicateKeyException dupEx = Mockito.mock(DuplicateKeyException.class);
        Mockito.when(ex.getCause()).thenReturn(dupEx);

        boolean res = serviceTools.isConstraintViolationExceptionFor(ex, null);

        Assertions.assertThat(res).isEqualTo(false);
    }

    @Test
    public void isConstraintViolationExceptionNotFoundConstraint() throws Exception {

        DataAccessException ex = Mockito.mock(DataAccessException.class);
        DuplicateKeyException dupEx = Mockito.mock(DuplicateKeyException.class);
        Mockito.when(ex.getCause()).thenReturn(dupEx);
        Mockito.when(ex.getMessage()).thenReturn("message with myConstraintName that is the error");

        boolean res = serviceTools.isConstraintViolationExceptionFor(ex, "myConstraintError");

        Assertions.assertThat(res).isEqualTo(false);
    }

    @Test
    public void isConstraintViolationExceptionFoundConstraint() throws Exception {

        DataAccessException ex = Mockito.mock(DataAccessException.class);
        DuplicateKeyException dupEx = Mockito.mock(DuplicateKeyException.class);
        Mockito.when(ex.getCause()).thenReturn(dupEx);
        Mockito.when(ex.getMessage()).thenReturn("message with myConstraintName that is the error");

        boolean res = serviceTools.isConstraintViolationExceptionFor(ex, "myConstraintName");

        Assertions.assertThat(res).isEqualTo(true);
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

        @NotNull
        @NotBlank
        private String name = null;

        @NotNull
        @AssertFalse
        private Boolean test = false;

        public MyObjectToValidate(String name) {
            this.name = name;
        }

    }

}

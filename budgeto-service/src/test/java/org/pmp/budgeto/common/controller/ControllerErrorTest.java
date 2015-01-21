package org.pmp.budgeto.common.controller;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.pmp.budgeto.common.domain.DomainConflictException;
import org.pmp.budgeto.common.domain.DomainException;
import org.pmp.budgeto.common.domain.DomainValidationError;
import org.pmp.budgeto.common.domain.DomainValidationException;
import org.pmp.budgeto.common.controller.ControllerError;


public class ControllerErrorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void withNullParamMsg() throws Exception {

        expectedException.expect(NullPointerException.class);

        ControllerError error = new ControllerError(null, null);
    }

    @Test
    public void withNullParamEx() throws Exception {

        expectedException.expect(NullPointerException.class);

        ControllerError error = new ControllerError("", null);
    }

    @Test
    public void withParamException() throws Exception {

        ControllerError error = new ControllerError("the message", new NullPointerException());

        Assertions.assertThat(error.getMessage()).isEqualTo("the message");
        Assertions.assertThat(error.getType()).isEqualTo("unknown");
        Assertions.assertThat(error.getException()).isNull();
        Assertions.assertThat(error.getExceptionType()).isEqualTo(NullPointerException.class.getSimpleName());
        Assertions.assertThat(error.getValidationErros()).hasSize(0);
    }

    @Test
    public void withParamExceptionWithMessage() throws Exception {

        ControllerError error = new ControllerError("the message", new NullPointerException("an error"));

        Assertions.assertThat(error.getMessage()).isEqualTo("the message");
        Assertions.assertThat(error.getType()).isEqualTo("unknown");
        Assertions.assertThat(error.getException()).isEqualTo("an error");
        Assertions.assertThat(error.getExceptionType()).isEqualTo(NullPointerException.class.getSimpleName());
        Assertions.assertThat(error.getValidationErros()).hasSize(0);
    }

    @Test
    public void withParamServiceException() throws Exception {

        ControllerError error = new ControllerError("the domain ex message", new DomainException("an error"));


        Assertions.assertThat(error.getMessage()).isEqualTo("the domain ex message");
        Assertions.assertThat(error.getType()).isEqualTo("server");
        Assertions.assertThat(error.getException()).isEqualTo("an error");
        Assertions.assertThat(error.getExceptionType()).isEqualTo(DomainException.class.getSimpleName());
        Assertions.assertThat(error.getValidationErros()).hasSize(0);
    }

    @Test
    public void withParamServiceValidationException() throws Exception {

        ControllerError error = new ControllerError("the domain message", new DomainValidationException("an error", new DomainValidationError("fieldName", "required")));


        Assertions.assertThat(error.getMessage()).isEqualTo("the domain message");
        Assertions.assertThat(error.getType()).isEqualTo("validation");
        Assertions.assertThat(error.getException()).isEqualTo("an error");
        Assertions.assertThat(error.getExceptionType()).isEqualTo(DomainValidationException.class.getSimpleName());
        Assertions.assertThat(error.getValidationErros()).hasSize(1);
    }

    @Test
    public void withParamServiceConflictException() throws Exception {

        ControllerError error = new ControllerError("the conflict message", new DomainConflictException("an error", new DomainValidationError("fieldName", "required")));


        Assertions.assertThat(error.getMessage()).isEqualTo("the conflict message");
        Assertions.assertThat(error.getType()).isEqualTo("conflict");
        Assertions.assertThat(error.getException()).isEqualTo("an error");
        Assertions.assertThat(error.getExceptionType()).isEqualTo(DomainConflictException.class.getSimpleName());
        Assertions.assertThat(error.getValidationErros()).hasSize(1);
    }

}

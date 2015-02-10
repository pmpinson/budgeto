package org.pmp.budgeto.common.controller;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.pmp.budgeto.common.domain.DomainValidationError;
import org.pmp.budgeto.common.domain.DomainValidationException;


public class ControllerErrorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void noErrorsWithNullParamsType() throws Exception {

        expectedException.expect(NullPointerException.class);

        ControllerError error = new ControllerError(null, null, null);
    }

    @Test
    public void noErrorsWithNullParamMessage() throws Exception {

        expectedException.expect(NullPointerException.class);

        ControllerError error = new ControllerError("mytype", null, null);
    }

    @Test
    public void noErrorsWithNullException() throws Exception {

        expectedException.expect(NullPointerException.class);

        ControllerError error = new ControllerError("mytype", "the message", null);
    }

    @Test
    public void noErrorsNormal() throws Exception {

        ControllerError error = new ControllerError("mytype", "the message", new NullPointerException());

        Assertions.assertThat(error.getMessage()).isEqualTo("the message");
        Assertions.assertThat(error.getType()).isEqualTo("mytype");
        Assertions.assertThat(error.getException()).isNull();
        Assertions.assertThat(error.getExceptionType()).isEqualTo(NullPointerException.class.getSimpleName());
        Assertions.assertThat(error.getValidationErros()).hasSize(0);
    }












    @Test
    public void withErrorsWithNullParamsType() throws Exception {

        expectedException.expect(NullPointerException.class);

        ControllerError error = new ControllerError(null, null, null, null);
    }

    @Test
    public void withErrorsWithNullParamMessage() throws Exception {

        expectedException.expect(NullPointerException.class);

        ControllerError error = new ControllerError("mytype", null, null, null);
    }

    @Test
    public void withErrorsWithNullException() throws Exception {

        expectedException.expect(NullPointerException.class);

        ControllerError error = new ControllerError("mytype", "the message", null, null);
    }

    @Test
    public void withErrorsWithNullValidationErrors() throws Exception {

        expectedException.expect(NullPointerException.class);

        ControllerError error = new ControllerError("mytype", "the message", new NullPointerException(), null);
    }

    @Test
    public void withErrorsNormal() throws Exception {

        ControllerError error = new ControllerError("mytype", "the message", new NullPointerException(), new DomainValidationException("an error", new DomainValidationError("fieldName", "required")).getConstraintViolations());

        Assertions.assertThat(error.getMessage()).isEqualTo("the message");
        Assertions.assertThat(error.getType()).isEqualTo("mytype");
        Assertions.assertThat(error.getException()).isNull();
        Assertions.assertThat(error.getExceptionType()).isEqualTo(NullPointerException.class.getSimpleName());
        Assertions.assertThat(error.getValidationErros()).hasSize(1);
    }

}

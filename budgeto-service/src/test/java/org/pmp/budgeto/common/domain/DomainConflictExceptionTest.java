package org.pmp.budgeto.common.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;


public class DomainConflictExceptionTest {

    @Test
    public void constructor() throws Exception {

        DomainValidationError error = new DomainValidationError("myFiled", (String[]) null);
        DomainConflictException domainException = new DomainConflictException("msg", error);

        Assertions.assertThat(domainException.getMessage()).isEqualTo("msg");
        Assertions.assertThat(domainException.getConstraintViolations()).isEqualTo(error);
        Assertions.assertThat(domainException.getCause()).isNull();
    }

    @Test
    public void constructorWithCause() throws Exception {

        NullPointerException nullEx = new NullPointerException();
        DomainValidationError error = new DomainValidationError("myFiled", (String[]) null);
        DomainConflictException domainException = new DomainConflictException("msg", nullEx, error);

        Assertions.assertThat(domainException.getMessage()).isEqualTo("msg");
        Assertions.assertThat(domainException.getConstraintViolations()).isEqualTo(error);
        Assertions.assertThat(domainException.getCause()).isEqualTo(nullEx);
    }

}

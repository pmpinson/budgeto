package org.pmp.budgeto.common.domain;

import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;


public class DomainValidationExceptionTest {

    @Test
    public void constructor() throws Exception {

        DomainValidationError[] errors = ArrayUtils.toArray(new DomainValidationError("myFiled", (String[]) null));
        DomainValidationException domainException = new DomainValidationException("msg", errors);

        Assertions.assertThat(domainException.getMessage()).isEqualTo("msg");
        Assertions.assertThat(domainException.getConstraintViolations()).isEqualTo(errors);
        Assertions.assertThat(domainException.getCause()).isNull();
    }

    @Test
    public void constructorWithCause() throws Exception {

        NullPointerException nullEx = new NullPointerException();
        DomainValidationError[] errors = ArrayUtils.toArray(new DomainValidationError("myFiled", (String[]) null));
        DomainValidationException domainException = new DomainValidationException("msg", nullEx, errors);

        Assertions.assertThat(domainException.getMessage()).isEqualTo("msg");
        Assertions.assertThat(domainException.getConstraintViolations()).isEqualTo(errors);
        Assertions.assertThat(domainException.getCause()).isEqualTo(nullEx);
    }

}

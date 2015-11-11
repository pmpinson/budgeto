package org.pmp.budgeto.common.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;


public class DomainExceptionTest {

    @Test
    public void constructor() throws Exception {

        DomainException domainException = new DomainException("msg");

        Assertions.assertThat(domainException.getMessage()).isEqualTo("msg");
        Assertions.assertThat(domainException.getCause()).isNull();
    }

    @Test
    public void constructorWithCause() throws Exception {

        NullPointerException nullEx = new NullPointerException();
        DomainException domainException = new DomainException("msg", nullEx);

        Assertions.assertThat(domainException.getMessage()).isEqualTo("msg");
        Assertions.assertThat(domainException.getCause()).isEqualTo(nullEx);
    }

}

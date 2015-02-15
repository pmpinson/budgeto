package org.pmp.budgeto.common.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;


public class DomainNotFoundExceptionTest {

    @Test
    public void constructor() throws Exception {

        DomainNotFoundException domainException = new DomainNotFoundException("msg");

        Assertions.assertThat(domainException.getMessage()).isEqualTo("msg");
        Assertions.assertThat(domainException.getCause()).isNull();
    }

}

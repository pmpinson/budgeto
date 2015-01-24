package org.pmp.budgeto.common.domain;

import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;


public class DomainValidationErrorTest {

    @Test
    public void withNullParam() throws Exception {

        DomainValidationError error = new DomainValidationError(null, (String[]) null);

        Assertions.assertThat(error.getField()).isEqualTo("_all");
        Assertions.assertThat(error.getErrors()).hasSize(0);
    }

    @Test
    public void withNameParam() throws Exception {

        DomainValidationError error = new DomainValidationError("myfield", (String[]) null);

        Assertions.assertThat(error.getField()).isEqualTo("myfield");
        Assertions.assertThat(error.getErrors()).hasSize(0);
    }

    @Test
    public void withErrorParam() throws Exception {

        DomainValidationError error = new DomainValidationError("myfield", "msg1", "msg2");

        Assertions.assertThat(error.getField()).isEqualTo("myfield");
        Assertions.assertThat(error.getErrors()).hasSize(2);
        Assertions.assertThat(error.getErrors()).containsExactly(ArrayUtils.toArray("msg1", "msg2"));
    }

}

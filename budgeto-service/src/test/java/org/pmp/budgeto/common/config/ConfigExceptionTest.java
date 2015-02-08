package org.pmp.budgeto.common.config;

import org.assertj.core.api.Assertions;
import org.junit.Test;


public class ConfigExceptionTest {

    @Test
    public void constructorWithCause() throws Exception {

        NullPointerException nullEx = new NullPointerException();
        ConfigException configException = new ConfigException("msg", nullEx);

        Assertions.assertThat(configException.getMessage()).isEqualTo("msg");
        Assertions.assertThat(configException.getCause()).isEqualTo(nullEx);
    }

}

package org.pmp.budgeto.common.controller;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.joda.time.DateTime;
import org.junit.Test;
import org.pmp.budgeto.test.TestTools;
import org.pmp.budgeto.test.config.TestConfig;
import org.pmp.budgeto.test.extractor.ConstraintViolationExtractor;
import org.springframework.web.context.request.RequestContextHolder;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


public class ApiTest {

    @Test
    public void generateLinks() throws Exception {

        Api object = new Api();

        RequestContextHolder.setRequestAttributes(TestTools.mockServletRequestAttributes());

        Assertions.assertThat(object.getLinks()).hasSize(3);

        Assertions.assertThat(object.getLinks().get(0).getRel()).isEqualTo("self");
        Assertions.assertThat(object.getLinks().get(0).getHref()).isEqualTo("http://local/myappserv1");

        Assertions.assertThat(object.getLinks().get(1).getRel()).isEqualTo("account");
        Assertions.assertThat(object.getLinks().get(1).getHref()).isEqualTo("http://local/myappserv1/account");

        Assertions.assertThat(object.getLinks().get(2).getRel()).isEqualTo("budget");
        Assertions.assertThat(object.getLinks().get(2).getHref()).isEqualTo("http://local/myappserv1/budget");

        RequestContextHolder.resetRequestAttributes();
    }

}

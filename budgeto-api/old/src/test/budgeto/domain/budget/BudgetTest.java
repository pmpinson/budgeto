package org.pmp.budgeto.domain.budget;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.pmp.budgeto.test.TestTools;
import org.pmp.budgeto.test.config.TestConfig;
import org.pmp.budgeto.test.extractor.ConstraintViolationExtractor;
import org.springframework.web.context.request.RequestContextHolder;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


public class BudgetTest {

    @Before
    public void setup() {
        TestConfig.init();
    }

    @Test
    public void getterSetter() throws Exception {

        Budget object = new Budget();
        object.setName("theName");
        object.setNote("theNote");

        Assertions.assertThat(object.getName()).isEqualTo("theName");
        Assertions.assertThat(object.getName()).isEqualTo(object.getName());
        Assertions.assertThat(object.getNote()).isEqualTo("theNote");
        Assertions.assertThat(object.getNote()).isEqualTo(object.getNote());
    }

    @Test
    public void equalsAndHashcodeDefault() throws Exception {

        Budget object1 = new Budget().setName("theName");
        Budget object2 = new Budget().setName("theName");

        Assertions.assertThat(object1).isEqualTo(object2);
        Assertions.assertThat(object1.hashCode()).isEqualTo(object2.hashCode());
    }

    @Test
    public void equalsAndHashcodeDifNote() throws Exception {

        Budget object1 = new Budget().setName("theName").setNote("theNote1");
        Budget object2 = new Budget().setName("theName").setNote("theNote2");

        Assertions.assertThat(object1).isEqualTo(object2);
        Assertions.assertThat(object1.hashCode()).isEqualTo(object2.hashCode());
    }

    @Test
    public void equalsSame() throws Exception {

        Budget object1 = new Budget().setName("theName");

        Assertions.assertThat(object1).isEqualTo(object1);
    }

    @Test
    public void notEqualsAndHashcodeDefault() throws Exception {

        Budget object1 = new Budget().setName("theName");
        Budget object2 = new Budget().setName("theName2");

        Assertions.assertThat(object1).isNotEqualTo(object2);
        Assertions.assertThat(object1.hashCode()).isNotEqualTo(object2.hashCode());
    }

    @Test
    public void notEqualsToNull() throws Exception {

        Budget object1 = new Budget().setName("theName");
        Budget object2 = null;

        Assertions.assertThat(object1).isNotEqualTo(object2);
    }

    @Test
    public void notEqualsOtherClass() throws Exception {

        Budget object1 = new Budget().setName("theName");

        Assertions.assertThat(object1).isNotEqualTo("test string");
    }

    @Test
    public void toStringDefault() throws Exception {

        Budget object = new Budget().setName("theName").setNote("theNote");

        Assertions.assertThat(object.toString()).isEqualTo("Budget[name=theName,note=theNote]");
    }

    @Test
    public void validationValid() throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Budget object = new Budget().setName("theName").setNote("theNote");

        Set<ConstraintViolation<Budget>> violations = validator.validate(object);

        Assertions.assertThat(violations).hasSize(0);
    }

    @Test
    public void validationNoName() throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Budget object = new Budget().setNote("theNote");

        Set<ConstraintViolation<Budget>> violations = validator.validate(object);

        Assertions.assertThat(violations).hasSize(2);
        Assertions.assertThat(violations).extracting(new ConstraintViolationExtractor()).contains(
                Tuple.tuple("name", "must not empty string (trim too)")
                , Tuple.tuple("name", "may not be null"));
    }

    @Test
    public void generateLinks() throws Exception {

        Budget object = new Budget().setName("theName");

        RequestContextHolder.setRequestAttributes(TestTools.mockServletRequestAttributes());

        Assertions.assertThat(object.getLinks()).hasSize(1);

        Assertions.assertThat(object.getLinks().get(0).getRel()).isEqualTo("self");
        Assertions.assertThat(object.getLinks().get(0).getHref()).isEqualTo("http://local/myappserv1/budget/theName");

        RequestContextHolder.resetRequestAttributes();
    }

}

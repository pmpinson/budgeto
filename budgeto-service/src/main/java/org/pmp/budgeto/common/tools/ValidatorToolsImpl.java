package org.pmp.budgeto.common.tools;

import org.springframework.stereotype.Component;

import javax.validation.*;
import java.util.Set;

/**
 * implementations of org common
 */
@Component
public class ValidatorToolsImpl implements ValidatorTools {

    @Override
    public <T> Set<ConstraintViolation<T>> validate(final T object) {
        Configuration<?> configuration = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = configuration
                .messageInterpolator(configuration.getDefaultMessageInterpolator())
                .buildValidatorFactory();

        Validator validator = factory.getValidator();
        return validator.validate(object);
    }

}

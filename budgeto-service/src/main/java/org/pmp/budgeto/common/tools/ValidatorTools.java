package org.pmp.budgeto.common.tools;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * common to simplify the use of JSR330 (validation)
 */
public interface ValidatorTools {

    <T> Set<ConstraintViolation<T>> validate(final T object);

}

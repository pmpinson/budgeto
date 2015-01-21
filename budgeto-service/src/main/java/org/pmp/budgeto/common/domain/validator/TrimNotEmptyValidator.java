package org.pmp.budgeto.common.domain.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Class with realise the validation for a String the must not be empty (trim too).
 */
public final class TrimNotEmptyValidator implements ConstraintValidator<TrimNotEmpty, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(TrimNotEmpty constraintAnnotation) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.trim().equals("")) {
            return false;
        }
        return true;
    }
}

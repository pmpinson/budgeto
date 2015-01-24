package org.pmp.budgeto.test.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.pmp.budgeto.common.domain.DomainConflictException;
import org.pmp.budgeto.common.domain.DomainException;
import org.pmp.budgeto.common.domain.DomainValidationException;


/**
 * matcher to control ServiceValidationException and ServiceConflictException
 */
public class ServiceExceptionValidationErrorsSizeMatcher extends TypeSafeMatcher<DomainException> {

    private int size;
    private int actualSize;

    public ServiceExceptionValidationErrorsSizeMatcher(int size) {
        this.size = size;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("expects validation error size ")
                .appendValue(size)
                .appendText(" and was ")
                .appendValue(actualSize);
    }

    @Override
    protected boolean matchesSafely(final DomainException exception) {
        if (exception instanceof DomainValidationException) {
            DomainValidationException ex = (DomainValidationException) exception;
            actualSize = ex.getConstraintViolations().length;
            return actualSize == size;
        } else if (exception instanceof DomainConflictException) {
            DomainConflictException ex = (DomainConflictException) exception;
            actualSize = ex.getConstraintViolations() == null ? 0 : 1;
            return actualSize == size;
        } else {
            throw new IllegalArgumentException("bad exception type");
        }
    }
}
package org.pmp.budgeto.test.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.pmp.budgeto.common.domain.DomainConflictException;
import org.pmp.budgeto.common.domain.DomainException;
import org.pmp.budgeto.common.domain.DomainValidationError;
import org.pmp.budgeto.common.domain.DomainValidationException;

import java.util.Arrays;

/**
 * matcher to control ServiceValidationException and ServiceConflictException message in ServiceValidationError
 */
public class ServiceExceptionValidationErrorContentMatcher extends TypeSafeMatcher<DomainException> {

    private String field;
    private String[] errors;
    private String[] actualErrors;

    public ServiceExceptionValidationErrorContentMatcher(String field, String[] errors) {
        this.field = field;
        this.errors = errors;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("expects validation error to contains for field ")
                .appendValue(field)
                .appendText(" : ")
                .appendValue(Arrays.toString(errors))
                .appendText(" and was ")
                .appendValue(Arrays.toString(actualErrors));
    }

    @Override
    protected boolean matchesSafely(final DomainException exception) {
        actualErrors = new String[]{};
        boolean res = false;
        if (exception instanceof DomainValidationException) {
            DomainValidationException ex = (DomainValidationException) exception;
            for (DomainValidationError error : ex.getConstraintViolations()) {
                if (error.getField().equals(field)) {
                    actualErrors = error.getErrors();
                    return Arrays.equals(actualErrors, errors);
                }
            }
            return res;
        } else  if (exception instanceof DomainConflictException) {
            DomainConflictException ex = (DomainConflictException) exception;
            if (ex.getConstraintViolations() != null) {
                if (ex.getConstraintViolations().getField().equals(field)) {
                    actualErrors = ex.getConstraintViolations().getErrors();
                    return Arrays.equals(actualErrors, errors);
                }
            }
            return res;
        } else {
            throw new IllegalArgumentException("bad exception type");
        }
    }
}



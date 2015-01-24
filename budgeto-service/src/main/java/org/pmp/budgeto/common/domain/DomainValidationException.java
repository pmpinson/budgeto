package org.pmp.budgeto.common.domain;

/**
 * excpetion to be throw when validation of bean is bad
 */
public class DomainValidationException extends DomainException {

    private final DomainValidationError[] validationErros;

    public <T> DomainValidationException(String message, DomainValidationError... validationErros) {
        super(message);
        this.validationErros = validationErros;
    }

    public <T> DomainValidationException(String message, Throwable exception, DomainValidationError... validationErros) {
        super(message, exception);
        this.validationErros = validationErros;
    }

    public DomainValidationError[] getConstraintViolations() {
        return validationErros;
    }

}

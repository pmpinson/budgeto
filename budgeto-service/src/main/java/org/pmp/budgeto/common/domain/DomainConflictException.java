package org.pmp.budgeto.common.domain;

/**
 * exception to manage conflict in data
 */
public class DomainConflictException extends DomainException {

    private final DomainValidationError validationErros;

    public <T> DomainConflictException(String message, DomainValidationError validationErros) {
        super(message);
        this.validationErros = validationErros;
    }

    public <T> DomainConflictException(String message, Throwable exception, DomainValidationError validationErros) {
        super(message, exception);
        this.validationErros = validationErros;
    }

    public DomainValidationError getConstraintViolations() {
        return validationErros;
    }

}

package org.pmp.budgeto.common.domain;

/**
 * default exception of domain
 */
public class DomainException extends Exception {

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable exception) {
        super(message, exception);
    }

}

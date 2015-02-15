package org.pmp.budgeto.common.domain;

/**
 * excpetion to be throw when domain entity not found
 */
public class DomainNotFoundException extends DomainException {

    public <T> DomainNotFoundException(String message) {
        super(message);
    }

}

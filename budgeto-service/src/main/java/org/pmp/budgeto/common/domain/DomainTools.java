package org.pmp.budgeto.common.domain;

import org.springframework.dao.DataAccessException;

/**
 * common for domain operation
 */
public interface DomainTools {

    /**
     * validate an object and throw good exception if not valid
     * @param object the object
     * @param objectDesc the desc of object to be throw in message
     * @throws DomainException if erreur may be ConflictException or ValidationException
     */
    void validate(Object object, String objectDesc) throws DomainException;

    /**
     * use to test if a specific exception is due to a certain contraint
     * @param parentException the exception
     * @param constraintName the contraint to compare to
     * @return true or false
     */
    boolean isConstraintViolationExceptionFor(DataAccessException parentException, String constraintName);

}

package org.pmp.budgeto.common.controller;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.pmp.budgeto.common.domain.DomainConflictException;
import org.pmp.budgeto.common.domain.DomainException;
import org.pmp.budgeto.common.domain.DomainNotFoundException;
import org.pmp.budgeto.common.domain.DomainValidationError;
import org.pmp.budgeto.common.domain.DomainValidationException;

/**
 * dto to give when an error occur in app
 */
@ApiModel(value = "Exception", description = "Information on error")
public class ControllerError {

    @ApiModelProperty(value = "message", notes = "description of the error", required = true)
    private final String message;

    @ApiModelProperty(value = "type", notes = "type of the error", required = true)
    private final String type;

    @ApiModelProperty(value = "exception", notes = "server exception message", required = true)
    private final String exception;

    @ApiModelProperty(value = "exceptionType", notes = "server exception type", required = true)
    private final String exceptionType;

    @ApiModelProperty(value = "validationErros", notes = "list of validation errors")
    private final DomainValidationError[] validationErros;

    public ControllerError(String message, Exception exception) {
        this.message = Validate.notNull(message);
        this.exception = Validate.notNull(exception).getMessage();
        this.exceptionType = Validate.notNull(exception).getClass().getSimpleName();

        // manage the type by the class of exception
        if (exception instanceof DomainException) {
            if (exception instanceof DomainValidationException) {
                this.type = "validation";
                DomainValidationException s = (DomainValidationException) exception;
                validationErros = s.getConstraintViolations();
            } else if (exception instanceof DomainConflictException) {
                this.type = "conflict";
                DomainConflictException s = (DomainConflictException) exception;
                validationErros = ArrayUtils.toArray(s.getConstraintViolations());
            } else if (exception instanceof DomainNotFoundException) {
                this.type = "notfound";
                DomainNotFoundException s = (DomainNotFoundException) exception;
                validationErros = new DomainValidationError[]{};
            } else {
                this.type = "server";
                validationErros = new DomainValidationError[]{};
            }
        } else {
            this.type = "unknown";
            validationErros = new DomainValidationError[]{};
        }
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getException() {
        return exception;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public DomainValidationError[] getValidationErros() {
        return ArrayUtils.clone(validationErros);
    }

}

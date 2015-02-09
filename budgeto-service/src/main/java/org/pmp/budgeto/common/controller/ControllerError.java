package org.pmp.budgeto.common.controller;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.pmp.budgeto.common.domain.DomainValidationError;

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

    public ControllerError(String type, String message, Exception exception) {
        this.type = Validate.notNull(type);
        this.message = Validate.notNull(message);
        this.exception = Validate.notNull(exception).getMessage();
        this.exceptionType = Validate.notNull(exception).getClass().getSimpleName();
        this.validationErros = new DomainValidationError[]{};
    }

    public ControllerError(String type, String message, Exception exception, DomainValidationError[] validationErros) {
        this.type = Validate.notNull(type);
        this.message = Validate.notNull(message);
        this.exception = Validate.notNull(exception).getMessage();
        this.exceptionType = Validate.notNull(exception).getClass().getSimpleName();
        this.validationErros = Validate.notNull(validationErros);
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

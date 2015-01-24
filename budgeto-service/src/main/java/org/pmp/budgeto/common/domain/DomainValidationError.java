package org.pmp.budgeto.common.domain;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.ObjectUtils;

/**
 * contact of validation or conflict error, use to merge all message from same field or object
 */
@ApiModel(value = "ValidationError", description = "describe the validation error")
public class DomainValidationError {

    public static final String FIELD_ALL = "_all";

    @ApiModelProperty(value = "field", notes = "the field where the error occured, maybe _all if note assigned to specific field, default _all", required = true)
    private final String field;

    @ApiModelProperty(value = "errors", notes = "the list of erros on the field, default empty array", required = true)
    private final String[] errors;

    public DomainValidationError(String field, String... errors) {
        this.field = ObjectUtils.defaultIfNull(field, FIELD_ALL);
        this.errors = ObjectUtils.defaultIfNull(errors, new String[]{});
    }

    public String getField() {
        return field;
    }

    public String[] getErrors() {
        return errors;
    }

}

package org.pmp.budgeto.domain.account;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;
import org.pmp.budgeto.common.domain.validator.TrimNotEmpty;
import org.pmp.budgeto.common.tools.DateTools;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;


/**
 * domain object representing a account
 */
@Document
@ApiModel(value = "Account", description = "Object describing an account")
public class Operation {

    @NotNull
    @ApiModelProperty(value = "date", notes = "date of the operation", required = true, dataType = "[year, month, day]")
    private DateTime date;

    @TrimNotEmpty
    @ApiModelProperty(value = "label", notes = "description of the operation", required = true)
    private String label;

    public DateTime getDate() {
        return date;
    }

    public Operation setDate(DateTime date) {
        this.date = DateTools.toUTC(DateTools.truncateTime(date));
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Operation setLabel(String label) {
        this.label = label;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}

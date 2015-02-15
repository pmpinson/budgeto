package org.pmp.budgeto.domain.account;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;
import org.pmp.budgeto.common.domain.Domain;
import org.pmp.budgeto.common.domain.validator.TrimNotEmpty;
import org.pmp.budgeto.common.tools.DateTools;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;


/**
 * domain object representing a account
 */
@Document
@ApiModel(value = "Operation", description = "Operation occured on an account")
public class Operation extends Domain {

    @Transient
    private DateTools dateTools;

    @NotNull
    @ApiModelProperty(value = "date of the operation, format [yyyy-MM-dd'T'HH:mm:ssZ]", required = true)
    private DateTime date;

    @TrimNotEmpty
    @ApiModelProperty(value = "description of the operation", required = true)
    private String label;

    /**
     * use by mongo to load
     */
    private Operation() {
    }

    /**
     * use extenaly
     *
     * @param dateTools
     */
    public Operation(DateTools dateTools) {
        this.dateTools = Validate.notNull(dateTools);
    }

    @Override
    protected void generateLinks() {
    }

    public DateTime getDate() {
        return date;
    }

    public Operation setDate(DateTime date) {
        this.date = dateTools.toUTCDate(date);
        return this;
    }

    public Operation setDateString(String date) {
        this.date = dateTools.toUTCDate(dateTools.getFormatterDate().parseDateTime(date));
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("label", String.valueOf(label))
                .append("date", dateTools.getFormatterDatetimeWithzone().print(date))
                .toString();
    }

}

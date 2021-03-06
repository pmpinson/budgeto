package org.pmp.budgeto.domain.budget;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.pmp.budgeto.common.domain.Domain;
import org.pmp.budgeto.common.domain.validator.TrimNotEmpty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.mvc.BasicLinkBuilder;


/**
 * domain object representing a budget to estimate a budget
 */
@Document
@ApiModel(value = "Budget", description = "Object describing a budget (future and perspective)")
public class Budget extends Domain {

    public static final String UNIQUE_IDX_NAME = "budgetUniqueName";

    @TrimNotEmpty
    @Indexed(unique = true, name = UNIQUE_IDX_NAME)
    @ApiModelProperty(value = "unique name of a budget", required = true)
    private String name;

    @ApiModelProperty(value = "description of the budget")
    private String note;

    @Override
    protected void generateLinks() {
        add(BasicLinkBuilder.linkToCurrentMapping().slash("budget").slash(String.valueOf(name)).withSelfRel());
    }

    public String getName() {
        return name;
    }

    public Budget setName(String name) {
        this.name = name;
        return this;
    }

    public String getNote() {
        return note;
    }

    public Budget setNote(String note) {
        this.note = note;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Budget rhs = (Budget) obj;
        return new EqualsBuilder()
                .append(name, rhs.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(name).
                toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", String.valueOf(name))
                .append("note", String.valueOf(note))
                .toString();
    }

}

package org.pmp.budgeto.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.pmp.budgeto.common.domain.validator.TrimNotEmpty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;


/**
 * domain object representing a account
 */
@Document
@ApiModel(value = "Account", description = "Object describing an account")
public class Account {

    public static final String UNIQUE_IDX_NAME = "accountUniqueName";

    @TrimNotEmpty
    @Indexed(unique = true, name = UNIQUE_IDX_NAME)
    @ApiModelProperty(value = "name", notes = "unique name of an account", required = true)
    private String name;

    @ApiModelProperty(value = "note", notes = "description of the account")
    private String note;

    @Valid
    private Set<Operation> operations = new HashSet<>();

    public String getName() {
        return name;
    }

    public Account setName(String name) {
        this.name = name;
        return this;
    }

    public String getNote() {
        return note;
    }

    public Account setNote(String note) {
        this.note = note;
        return this;
    }

    @JsonIgnore
    public Set<Operation> getOperations() {
        return CollectionHelper.newHashSet(operations);
    }

    public Account addOperations(Operation operation) {
        this.operations.add(operation);
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
        Account rhs = (Account) obj;
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}

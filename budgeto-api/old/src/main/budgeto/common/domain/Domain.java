package org.pmp.budgeto.common.domain;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.hateoas.Link;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class of domain object
 */
@ApiModel(value = "Domain", description = "Base class of all object")
abstract public class Domain {

    @Transient
    @ApiModelProperty(value = "unique name of an account", required = true)
    private final List<Link> links = new ArrayList();

    protected void add(Link link) {
        Assert.notNull(link, "Link must not be null!");
        this.links.add(link);
    }

    public List<Link> getLinks() {
        links.clear();
        generateLinks();
        return this.links;
    }

    abstract protected void generateLinks();

}

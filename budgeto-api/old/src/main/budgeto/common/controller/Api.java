package org.pmp.budgeto.common.controller;

import com.wordnik.swagger.annotations.ApiModel;
import org.pmp.budgeto.common.domain.Domain;
import org.springframework.hateoas.mvc.BasicLinkBuilder;

/**
 * helper class for discovering apis
 */
@ApiModel(value = "Api", description = "Discovering resource in api")
public class Api extends Domain {

    @Override
    protected void generateLinks() {
        add(BasicLinkBuilder.linkToCurrentMapping().slash("").withSelfRel());
        add(BasicLinkBuilder.linkToCurrentMapping().slash("account").withRel("account"));
        add(BasicLinkBuilder.linkToCurrentMapping().slash("budget").withRel("budget"));
    }
}

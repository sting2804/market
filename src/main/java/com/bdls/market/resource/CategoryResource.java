package com.bdls.market.resource;

import com.bdls.market.controller.CategoryController;
import com.bdls.market.domain.Category;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class CategoryResource extends ResourceSupport{
    private final Category category;

    @JsonCreator
    public CategoryResource(Category category) {
        this.category = category;
        this.add(linkTo(methodOn(CategoryController.class).readCategoryById(category.getObjectId())).withSelfRel());
    }

    public Category getCategory() {
        return category;
    }
}

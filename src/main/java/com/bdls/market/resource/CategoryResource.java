package com.bdls.market.resource;

import com.bdls.market.controller.CategoryController;
import com.bdls.market.domain.Category;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class CategoryResource extends ResourceSupport{
    private final Category category;

    public CategoryResource(Category category) {
        this.category = category;
        this.add(linkTo(CategoryController.class, category.getName()).withRel("categories"));
        this.add(linkTo(methodOn(CategoryController.class, category.getName()).readCategoryById(category.getId())).withSelfRel());
    }
}

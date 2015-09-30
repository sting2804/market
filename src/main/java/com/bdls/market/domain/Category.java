package com.bdls.market.domain;

import com.bdls.market.controller.CategoryController;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * категория товаров
 */
@Entity
@JsonSerialize
public class Category extends ResourceSupport{

    /**
     * список продуктов в данной категории
     */
    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Integer version;
    /**
     * название категории. Максимум 255 символов. Обязательное поле
     */
    @Size(max = 255)
    @Column(nullable = false, unique = true)
    private String name;
    /**
     * описание категории, не обязательное поле
     */
    @Size(max = 500)
    private String description;

    /**
     * для JPA
     */
    public Category() {
        name = null;
        description = null;
    }
    @JsonCreator
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.add(linkTo(methodOn(CategoryController.class).readCategoryById(getObjectId())).withSelfRel());
    }

    @JsonProperty("id")
    public Long getObjectId(){
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

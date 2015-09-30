package com.bdls.market.controller;

import com.bdls.market.domain.Category;
import com.bdls.market.repository.CategoryRepository;
import com.bdls.market.resource.CategoryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Resources<Category> readCategories(){
        List<Category> categoryList = ((List<Category>) categoryRepository.findAll())
                    .stream()
                    .map(category -> {category.add(linkTo(methodOn(CategoryController.class).readCategoryById(category.getObjectId())).withSelfRel()); return category;})
                    .collect(Collectors.toList());
        Link categoryLink = linkTo(CategoryController.class).withSelfRel();
        return new Resources<>(categoryList, categoryLink);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Category readCategoryById(@PathVariable Long id){
        Category category = categoryRepository.findOne(id);
        category.add(linkTo(methodOn(CategoryController.class).readCategoryById(id)).withSelfRel());
        return category;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> create(@RequestParam("name") String name, @RequestParam(value = "description", required = false) String description){
        Category result = categoryRepository.save(new Category(name, description));
        if(result!=null && result.getId() != null) {
            HttpHeaders httpHeaders = new HttpHeaders();
            Link forOneCategory = new CategoryResource(result).getLink("self");
            httpHeaders.setLocation(URI.create(forOneCategory.getHref()));
            return new ResponseEntity<>(result, httpHeaders, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>("Unexpected error.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable Long id){
        try{
            categoryRepository.delete(id);
            HttpHeaders httpHeaders = new HttpHeaders();
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.NO_CONTENT);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

}

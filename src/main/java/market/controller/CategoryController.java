package market.controller;

import market.domain.Category;
import market.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Category> readCategories(){
        return categoryRepository.findAll();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    Category readCategoryById(@PathVariable Long id){
        return categoryRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> create(@RequestParam("name") String name, @RequestParam(value = "description", required = false) String description){
        Category result = categoryRepository.save(new Category(name, description));
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.CREATED);
    }

}

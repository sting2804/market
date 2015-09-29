package market.controller;

import market.domain.Category;
import market.repository.CategoryRepository;
import market.resource.CategoryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Resources<CategoryResource> readCategories(){
        List<CategoryResource> categoryResourceList = ((Collection<Category>)categoryRepository.findAll())
                    .stream()
                    .map(CategoryResource::new)
                    .collect(Collectors.toList());
        return new Resources<>(categoryResourceList);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CategoryResource readCategoryById(@PathVariable Long id){
        return new CategoryResource(categoryRepository.findOne(id));
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

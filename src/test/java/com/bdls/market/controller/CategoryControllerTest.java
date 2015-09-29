package com.bdls.market.controller;

import junit.framework.Assert;
import junit.framework.TestCase;
import com.bdls.market.MarketApplication;
import com.bdls.market.domain.Category;
import com.bdls.market.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MarketApplication.class)
@WebAppConfiguration
public class CategoryControllerTest extends TestCase {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Category category;

    private List<Category> categoryList = new ArrayList<>();

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.categoryRepository.deleteAll();

        this.category = categoryRepository.save(new Category("name", "description"));
        this.categoryList.add(category);
        this.categoryList.add(categoryRepository.save(new Category("name2", null)));
    }

    @Test
    public void readSingleCategory() throws Exception {
        mockMvc.perform(get("/" + "categories/"
                + this.category.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.category.getId().longValue())))
                .andExpect(jsonPath("$.version", is(this.category.getVersion().intValue())))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.description", is("description")));
    }

    @Test
    public void readCategories() throws Exception {
        mockMvc.perform(get("/" + "categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.categoryList.get(0).getId().longValue())))
                .andExpect(jsonPath("$[0].version", is(this.categoryList.get(0).getVersion().intValue())))
                .andExpect(jsonPath("$[0].name", is("name")))
                .andExpect(jsonPath("$[0].description", is("description")))
                .andExpect(jsonPath("$[1].id", is(this.categoryList.get(1).getId().longValue())))
                .andExpect(jsonPath("$[1].version", is(this.categoryList.get(1).getVersion().intValue())))
                .andExpect(jsonPath("$[1].name", is("name2")))
                .andExpect(jsonPath("$[1].description", isEmptyOrNullString()));
    }

    @Test
    public void createCategory() throws Exception {
        String categoryJson = json(new Category("name3", null));
        this.mockMvc.perform(post("/categories")
                .contentType(contentType)
                .content(categoryJson))
                .andExpect(status().isCreated());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
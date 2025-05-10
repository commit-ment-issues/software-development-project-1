package fi.haagahelia.quizzler;

import fi.haagahelia.quizzler.domain.Category;
import fi.haagahelia.quizzler.domain.CategoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        categoryRepository.deleteAll();
    }

    @Test
    public void getAllCategoriesReturnsEmptyListWhenNoCategoriesExist() throws Exception {
        mockMvc.perform(get("/api/categories")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void getAllCategoriesReturnsListOfCategoriesWhenCategoriesExist() throws Exception {
        Category category1 = new Category("Test category 1", "Description 1");
        Category category2 = new Category("Test category 2", "Description 2");
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        mockMvc.perform(get("/api/categories")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Test category 1"))
                .andExpect(jsonPath("$[1].name").value("Test category 2"));
    }

    @Test
    public void getCategoryByIdReturnsCorrectCategory() throws Exception {
        Category category1 = new Category("Test category 1", "Description 1");
        Category category2 = new Category("Test category 2", "Description 2");
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        Long categoryId = category2.getCategoryid();

        mockMvc.perform(get("/api/categories/{id}", categoryId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test category 2"));

    }

}

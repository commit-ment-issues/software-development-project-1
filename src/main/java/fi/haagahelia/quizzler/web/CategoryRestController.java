package fi.haagahelia.quizzler.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fi.haagahelia.quizzler.domain.CategoryRepository;
import fi.haagahelia.quizzler.domain.Category;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")

public class CategoryRestController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category with the provided id " + id + " does not exist"));
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

}

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import fi.haagahelia.quizzler.domain.Category;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Categories", description = "Operations for retrieving and manipulating categories")
public class CategoryRestController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Operation(
        summary = "Get a category by id", 
        description = "Returns the category with the provided id"
    )
    @ApiResponses(value={
        @ApiResponse(responseCode = "200", description = "Category with the provided id retrieved succesully"),
        @ApiResponse(responseCode = "400", description = "Category with the provided id does not exist")
    })
    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category with the provided id " + id + " does not exist"));
    }

    @Operation(
        summary = "Get a list of all categories", 
        description = "Returns a list of all the categories"
    )
    @ApiResponses(value={
        @ApiResponse(responseCode = "200", description = "List of categories retrieved succesully"),
        @ApiResponse(responseCode = "400", description = "List of categories does not exist")
    })
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

}

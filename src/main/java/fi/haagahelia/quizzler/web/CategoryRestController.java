package fi.haagahelia.quizzler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.haagahelia.quizzler.domain.CategoryRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")

public class CategoryRestController {
    @Autowired
    private CategoryRepository categoryRepository;

}

package fi.haagahelia.quizzler;

import fi.haagahelia.quizzler.domain.Category;
import fi.haagahelia.quizzler.domain.CategoryRepository;
import fi.haagahelia.quizzler.domain.Quiz;
import fi.haagahelia.quizzler.domain.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class QuizRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category testCategory;

    @BeforeEach
    public void setup() {
        quizRepository.deleteAll();
        categoryRepository.deleteAll();
        testCategory = categoryRepository.save(new Category("Test Category", null));
    }

    @Test
    public void getAllQuizzesReturnsEmptyListWhenNoQuizzesExist() throws Exception {
        mockMvc.perform(get("/api/quizzes/published")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void getAllQuizzesReturnsListOfQuizzesWhenPublishedQuizzesExist() throws Exception {
        Quiz quiz1 = new Quiz("Quiz 1", "Description 1", "CS101", 1, LocalDate.now(), testCategory);
        Quiz quiz2 = new Quiz("Quiz 2", "Description 2", "CS102", 1, LocalDate.now(), testCategory);
        quizRepository.save(quiz1);
        quizRepository.save(quiz2);

        mockMvc.perform(get("/api/quizzes/published")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Quiz 1"))
                .andExpect(jsonPath("$[1].name").value("Quiz 2"));
    }

    @Test
    public void getAllQuizzesDoesNotReturnUnpublishedQuizzes() throws Exception {
        Quiz published = new Quiz("Published Quiz", "This is published", "CS201", 1, LocalDate.now(), testCategory);
        Quiz unpublished = new Quiz("Unpublished Quiz", "This is not published", "CS202", 0, LocalDate.now(), testCategory);
        quizRepository.save(published);
        quizRepository.save(unpublished);

        mockMvc.perform(get("/api/quizzes/published")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Published Quiz"));
    }
}


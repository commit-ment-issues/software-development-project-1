package fi.haagahelia.quizzler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import javax.print.attribute.standard.Media;

import fi.haagahelia.quizzler.domain.AnswerDTO;
import fi.haagahelia.quizzler.domain.Answers;
import fi.haagahelia.quizzler.domain.AnswersRepository;
import fi.haagahelia.quizzler.domain.Category;
import fi.haagahelia.quizzler.domain.CategoryRepository;
import fi.haagahelia.quizzler.domain.Question;
import fi.haagahelia.quizzler.domain.QuestionRepository;
import fi.haagahelia.quizzler.domain.QuestionResultsDTO;
import fi.haagahelia.quizzler.domain.Quiz;
import fi.haagahelia.quizzler.domain.QuizRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AnswerRestControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnswersRepository answersRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Question testQuestion;
    private Category testCategory;
    private Answers testAnswer;
    private QuestionResultsDTO testQuestionResultsDTO;

    @BeforeEach
    public void setup(){
        answersRepository.deleteAll();
        quizRepository.deleteAll();
        testQuestion = questionRepository.save(
            new Question("Test question",
                         "Test Difficulty",
                         0,
                         0));
        testCategory = categoryRepository.save(new Category("Test Category", null));
    }

    @Test
    public void createAnswerSavesAnswerForPublishedQuiz() throws Exception{
        
        Quiz quiz1 = new Quiz("Test Quiz 1",
                             "Test Description",
                             "Test Code",
                             1,
                             LocalDate.now(),
                             testCategory);
        testAnswer = answersRepository.save(new Answers("Test Question", 1));
        quizRepository.save(quiz1);
        testQuestion.setQuiz(quiz1);
        testAnswer.setQuestion(testQuestion);

        this.mockMvc.perform(get("/api/question/answer/" + testAnswer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.text").value("Test Question"));

        List<Answers> answers = answersRepository.findAll();
        assertEquals(1, answers.size());
        assertEquals(testAnswer.getId(), answers.get(0).getId());
    }

    @Test
    public void createAnswerDoesNotSaveAnswerWithoutAnswerOption() throws Exception{
                
        Quiz quiz1 = new Quiz("Test Quiz 1",
                             "Test Description",
                             "Test Code",
                             1,
                             LocalDate.now(),
                             testCategory);
        quizRepository.save(quiz1);
        testQuestion.setQuiz(quiz1);

        this.mockMvc.perform(get("/api/question/answer/"))
                    .andExpect(status().isNotFound());

        List<Answers> answers = answersRepository.findAll();
        assertEquals(0, answers.size());
    }

    @Test
    public void createAnswerDoesNotSaveAnswerForNonExistingAnswerOption() throws Exception{

        Quiz quiz1 = new Quiz("Test Quiz 1",
                             "Test Description",
                             "Test Code",
                             1,
                             LocalDate.now(),
                             testCategory);
        quizRepository.save(quiz1);
        testQuestion.setQuiz(quiz1);

        this.mockMvc.perform(get("/api/question/answer/1"))
                    .andExpect(status().isNotFound());

        List<Answers> answers = answersRepository.findAll();
        assertEquals(0, answers.size());
    }


    //TODO: Check validation for saving answers to published and unpublished quizzes
    @Test
    public void createAnswerDoesNotSaveAnswerForNonPublishedQuiz() throws Exception{
        Quiz quiz1 = new Quiz("Test Quiz 1",
                             "Test Description",
                             "Test Code",
                             0,
                             LocalDate.now(),
                             testCategory);
        quizRepository.save(quiz1);
        testQuestion.setQuiz(quiz1);

        this.mockMvc.perform(get("/api/question/answer/"))
                    .andExpect(status().isBadRequest());

        List<Answers> answers = answersRepository.findAll();
        assertEquals(0, answers.size());
    }
}

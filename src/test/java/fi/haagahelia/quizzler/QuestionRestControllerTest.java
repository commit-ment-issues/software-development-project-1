package fi.haagahelia.quizzler;

import fi.haagahelia.quizzler.domain.Answers;
import fi.haagahelia.quizzler.domain.Question;
import fi.haagahelia.quizzler.domain.QuestionRepository;
import fi.haagahelia.quizzler.domain.Quiz;
import fi.haagahelia.quizzler.domain.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    public void setup() {
        questionRepository.deleteAll();
        quizRepository.deleteAll();
    }

    @Test
    public void getQuestionsByQuizIdReturnsEmptyListWhenQuizDoesNotHaveQuestions() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setName("Empty Quiz");
        quiz = quizRepository.save(quiz);

        mockMvc.perform(get("/api/quiz/" + quiz.getQuizId() + "/questions")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void getQuestionsByQuizIdReturnsListOfQuestionsWhenQuizHasQuestions() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setName("Quiz with Questions");
        quiz = quizRepository.save(quiz);

        Question question1 = new Question("Is this a question?", "easy", 0, 0);
        question1.setQuiz(quiz);
        Answers answer1 = new Answers("Yes", 1);
        answer1.setQuestion(question1);
        Answers answer2 = new Answers("No", 0);
        answer2.setQuestion(question1);
        question1.setAnswers(List.of(answer1, answer2));

        Question question2 = new Question("What is Spring Boot?", "medium", 0, 0);
        question2.setQuiz(quiz);
        Answers answer3 = new Answers("A Java framework.", 1);
        answer3.setQuestion(question2);
        Answers answer4 = new Answers("A type of footwear.", 0);
        answer4.setQuestion(question2);
        question2.setAnswers(List.of(answer3, answer4));

        questionRepository.saveAll(List.of(question1, question2));

        mockMvc.perform(get("/api/quiz/" + quiz.getQuizId() + "/questions")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].questionText").value("Is this a question?"))
                .andExpect(jsonPath("$[0].answers.length()").value(2))
                .andExpect(jsonPath("$[0].answers[0].text").value("Yes"))
                .andExpect(jsonPath("$[0].answers[0].status").value(1))
                .andExpect(jsonPath("$[1].questionText").value("What is Spring Boot?"))
                .andExpect(jsonPath("$[1].answers.length()").value(2))
                .andExpect(jsonPath("$[1].answers[0].text").value("A Java framework."))
                .andExpect(jsonPath("$[1].answers[0].status").value(1));
    }

    @Test
    public void getQuestionsByQuizIdReturnsErrorWhenQuizDoesNotExist() throws Exception {
        Long falseQuizId = 999L;

        mockMvc.perform(get("/api/quiz/" + falseQuizId + "/questions")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

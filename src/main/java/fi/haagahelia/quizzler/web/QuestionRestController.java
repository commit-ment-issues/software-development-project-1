package fi.haagahelia.quizzler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

import fi.haagahelia.quizzler.domain.Question;
import fi.haagahelia.quizzler.domain.QuestionRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class QuestionRestController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/quiz/{id}/questions")
    public List<Question> getQuestionsByQuizId(@PathVariable Long id) {
        List<Question> questions = questionRepository.findByQuiz_QuizId(id);

        if (questions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Quiz with the provided id " + id + " does not exist");
        }

        return questions;
    }

    @GetMapping("/quiz/questions")
    public List<Question> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();

        return questions;
    }
}

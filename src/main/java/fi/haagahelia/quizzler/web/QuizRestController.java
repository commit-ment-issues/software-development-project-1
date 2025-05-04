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

import fi.haagahelia.quizzler.domain.QuizRepository;
import fi.haagahelia.quizzler.domain.Quiz;
import fi.haagahelia.quizzler.domain.CategoryRepository;
import fi.haagahelia.quizzler.domain.QuestionResultsDTO;
import fi.haagahelia.quizzler.domain.Category;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class QuizRestController {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/quizzes/{id}")
    public Quiz getQuizById(@PathVariable Long id) {
        return quizRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Quiz with the provided id " + id + " does not exist"));
    }

    @GetMapping("/quizzes")
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @GetMapping("/quizzes/published")
    public List<Quiz> getPublishedQuizzes() {
        return quizRepository.findAllByPublishedStatus(1);
    }

    @GetMapping("/quizzes/categories/{id}")
    public List<Quiz> getQuizzesByCategoryId(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category with ID " + id + " not found"));

        List<Quiz> quizzes = quizRepository.findByCategory(category);

        if (quizzes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No quizzes found for category ID " + id);
        }

        return quizzes;
    }

    @GetMapping("/quizzes/{id}/results")
    public List<QuestionResultsDTO> getQuizResults(@PathVariable Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Quiz with the provided id " + id + " does not exist"));
    
        return quiz.getQuestions().stream().map(question -> 
            new QuestionResultsDTO(
                question.getQuestionId(),
                question.getQuestionText(),
                question.getDifficulty(),
                question.getTotalAnswers(),  
                question.getCorrectAnswers(),
                question.getWrongAnswers() 
            )
        ).toList();
    }

}

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name= "Questions", description = "Operations for retrieving and manipulating questions")
public class QuestionRestController {
    @Autowired
    private QuestionRepository questionRepository;

    @Operation(
        summary = "Get a list of questions by QuizId", 
        description = "Returns a list of questions with the provided QuizId"
    )
    @ApiResponses(value={
        @ApiResponse(responseCode = "200", description = "List of questions with the provided id retrieved succesully"),
        @ApiResponse(responseCode = "400", description = "List of questions with the provided id does not exist")
    })
    @GetMapping("/quiz/{id}/questions")
    public List<Question> getQuestionsByQuizId(@PathVariable Long id) {
        List<Question> questions = questionRepository.findByQuiz_QuizId(id);

        if (questions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Quiz with the provided id " + id + " does not exist");
        }

        return questions;
    }

    @Operation(
        summary = "Get a list of all the questions", 
        description = " Retuns a list of questions"
    )
    @ApiResponses(value={
        @ApiResponse(responseCode = "200", description = "List of questions retrieved succesully"),
        @ApiResponse(responseCode = "400", description = "List of questions does not exist")
    })
    @GetMapping("/quiz/questions")
    public List<Question> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();

        return questions;
    }
}

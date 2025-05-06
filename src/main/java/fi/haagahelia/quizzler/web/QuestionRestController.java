package fi.haagahelia.quizzler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

import fi.haagahelia.quizzler.domain.AnswerDTO;
import fi.haagahelia.quizzler.domain.Question;
import fi.haagahelia.quizzler.domain.QuestionRepository;
import fi.haagahelia.quizzler.domain.QuestionResultsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Questions", description = "Operations for retrieving and manipulating questions")
public class QuestionRestController {
    @Autowired
    private QuestionRepository questionRepository;

    @Operation(summary = "Get a list of questions by QuizId", description = "Returns a list of questions with the provided QuizId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of questions with the provided id retrieved succesully"),
            @ApiResponse(responseCode = "400", description = "List of questions with the provided id does not exist")
    })
    @GetMapping("/quiz/{id}/questions")
    public List<QuestionResultsDTO> getQuestionsByQuizId(@PathVariable Long id) {
        List<Question> questions = questionRepository.findByQuiz_QuizId(id);

        if (questions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Quiz with the provided id " + id + " does not exist");
        }

        return questions.stream()
                .map(QuestionResultsDTO::new)
                .toList();
    }

    @Operation(summary = "Get a list of all the questions", description = " Retuns a list of questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of questions retrieved succesully"),
            @ApiResponse(responseCode = "400", description = "List of questions does not exist")
    })
    @GetMapping("/quiz/questions")
    public List<Question> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();

        return questions;
    }

    @PutMapping("/question/updateanswer/{id}")
    public ResponseEntity<QuestionResultsDTO> updateQuestion(@PathVariable long id,
            @RequestBody QuestionResultsDTO updateResultsDTO) {
        Question updateQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Question with id " + id + "not found"));

        for (AnswerDTO answerDTO : updateResultsDTO.getAnswers()) {
            updateQuestion.getAnswers().stream()
                    .filter(answer -> answer.getId().equals(answerDTO.getId()))
                    .findFirst()
                    .ifPresent(answer -> {
                        if (answerDTO.getStatus() == 1) {
                            updateQuestion.setCorrectAnswers(updateQuestion.getCorrectAnswers() + 1);
                        }
                        updateQuestion.setTotalAnswers(updateQuestion.getTotalAnswers() + 1);
                    });
        }

        Question savedQuestion = questionRepository.save(updateQuestion);

        QuestionResultsDTO responseDTO = new QuestionResultsDTO(savedQuestion);

        return ResponseEntity.ok(responseDTO);
    }
}

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

import fi.haagahelia.quizzler.domain.Answers;
import fi.haagahelia.quizzler.domain.AnswersRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name= "Answers", description = "Operations for retrieving and manipulating answers")
public class AnswerRestController {
    @Autowired
    private AnswersRepository answerRepository;

    @Operation(
        summary = "Get answers by question id", 
        description = "Returns a list of answers with the provided question id"
    )
    @ApiResponses(value={
        @ApiResponse(responseCode = "200", description = "List of answers with the provided id retrieved succesully"),
        @ApiResponse(responseCode = "400", description = "List of answers with the provided id does not exist")
    })
    @GetMapping("/question/{id}/answers")
    public List<Answers> getAnswersByQuestionId(@PathVariable Long id) {
        List<Answers> answers = answerRepository.findByQuestionQuestionId(id);

        if (answers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Question with the provided id " + id + " does not exist");
        }
        return answers;
    }

    @GetMapping("/question/answer/{id}")
    public Answers getAnswerById(@PathVariable Long id){
        return answerRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Answer with the provided id " + id + " does not exist"));
    }

}

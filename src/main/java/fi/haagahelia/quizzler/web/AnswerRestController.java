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

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AnswerRestController {   
    @Autowired
    private AnswersRepository answerRepository;

    @GetMapping("/question/{id}/answers")
    public List<Answers> getAnswersByQuestionId(@PathVariable Long id) {
        List<Answers> answers = answerRepository.findByQuestionQuestionId(id);
        
        if (answers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question with the provided id " + id + " does not exist");
        }
        return answers;
    }

}

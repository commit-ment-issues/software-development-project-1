package fi.haagahelia.quizzler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

import fi.haagahelia.quizzler.domain.Answers;
import fi.haagahelia.quizzler.domain.AnswersRepository;
import fi.haagahelia.quizzler.domain.CreateSubmittedAnswerDTO;
import fi.haagahelia.quizzler.domain.Question;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Question with the provided id " + id + " does not exist");
        }
        return answers;
    }

    @PostMapping("/submittedanswer")
    public void CreateSubmittedAnswer(@RequestBody CreateSubmittedAnswerDTO dto) {
        Long optionId = dto.getAnswerOptionId();

        if (dto.getAnswerOptionId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Answer option ID is not provided.");
        }

        Answers selectedAnswerOption = answerRepository.findById(dto.getAnswerOptionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Answer option with the provided ID does not exist."));

        Question question = selectedAnswerOption.getQuestion();
        if (question == null || question.getQuiz() == null || question.getQuiz().getPublishedStatus() == null
                || question.getQuiz().getPublishedStatus() != 1) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Quiz is not published.");
        }

        //answerRepository.save(selectedAnswerOption); 
    }

}

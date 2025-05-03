package fi.haagahelia.quizzler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.haagahelia.quizzler.domain.Question;
import fi.haagahelia.quizzler.domain.QuestionRepository;

@Service
public class QuizService {
 
    @Autowired
    private QuestionRepository questionRepository;
 
    public void handleAnswer(long questionId, long answerId, boolean isCorrect) {
        // Hae kysymys
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Kysymystä ei löydy"));
 
        // Päivitä kysymyksen tilastot
        question.UpdateSubmittedAnswer( isCorrect); // Päivitä kysymyksen tilastot
        questionRepository.save(question); // Tallenna kysymys takaisin tietokantaan
    }
}

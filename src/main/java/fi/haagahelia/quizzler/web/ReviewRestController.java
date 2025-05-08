package fi.haagahelia.quizzler.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import fi.haagahelia.quizzler.domain.Category;
import fi.haagahelia.quizzler.domain.Quiz;
import fi.haagahelia.quizzler.domain.ReviewRepository;
import fi.haagahelia.quizzler.domain.QuizRepository;
import fi.haagahelia.quizzler.domain.Review;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ReviewRestController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private QuizRepository quizRepository;

    @GetMapping("/reviews")
    public List<Review> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        if (reviews.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No reviews found");
        }

        return reviews;
    }

    @GetMapping("/reviews/quiz/{id}")
public List<Review> getReviewsByQuizId(@PathVariable Long id) {
    Quiz quiz = quizRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Quiz with ID " + id + " not found"));

    return reviewRepository.findByQuiz_QuizId(id); 
}


    @PostMapping("/quizzes/{quizId}/reviews")
    public Review addReviewToQuiz(@PathVariable Long quizId, @RequestBody Review review) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Quiz with ID " + quizId + " not found"));

        review.setQuiz(quiz);
        return reviewRepository.save(review);
    }

}

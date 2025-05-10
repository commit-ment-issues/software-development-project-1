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

import fi.haagahelia.quizzler.domain.Quiz;
import fi.haagahelia.quizzler.domain.ReviewRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import fi.haagahelia.quizzler.domain.QuizRepository;
import fi.haagahelia.quizzler.domain.Review;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Reviews", description = "Operations for retrieving and manipulating Reviews")
public class ReviewRestController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Operation(summary = "Get a list of all the reviews", description = "Returns a list of reviews")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reviews retrieved succesully"),
            @ApiResponse(responseCode = "400", description = "Reviews do not exist")
    })
    @GetMapping("/reviews")
    public List<Review> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        if (reviews.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No reviews found");
        }

        return reviews;
    }


    @Operation(summary = "Get a list of reviews by QuizId", description = "Returns a list of reviews with QuizId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reviews retrieved succesully with the provided id"),
            @ApiResponse(responseCode = "400", description = "Reviews do not exist with the provided id")
    })
    @GetMapping("/reviews/quiz/{id}")
    public List<Review> getReviewsByQuizId(@PathVariable Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Quiz with ID " + id + " not found"));

        return reviewRepository.findByQuiz_QuizId(id);
    }


    @Operation(summary = "Add a review to a quiz with quizId", description = "Adds a review to a quiz with quizId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review added succesfully to a quiz with the provided id"),
            @ApiResponse(responseCode = "400", description = "Reviews coulnd't be added to a quiz with the provided id")
    })
    @PostMapping("/quizzes/{quizId}/reviews")
    public Review addReviewToQuiz(@PathVariable Long quizId, @RequestBody Review review) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Quiz with ID " + quizId + " not found"));

        review.setQuiz(quiz);
        return reviewRepository.save(review);
    }

}

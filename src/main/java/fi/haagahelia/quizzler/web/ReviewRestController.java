package fi.haagahelia.quizzler.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Reviews", description = "Operations for retrieving and manipulating Reviews")
public class ReviewRestController {

        @Autowired
        private ReviewRepository reviewRepository;

        @Autowired
        private QuizRepository quizRepository;

        @Operation(summary = "Get a list of all of the reviews", description = "Returns a list of reviews")
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


        @Operation(summary = "Get a list of reviews by quiz id", description = "Returns a list of reviews with quiz id")
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


        @Operation(summary = "Add a review to a quiz with quiz id", description = "Adds a review to a quiz with quiz id")
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


        @Operation(summary = "Get a review by id", description = "Returns a review with id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Reviews retrieved succesully with the provided id"),
                        @ApiResponse(responseCode = "400", description = "Review do not exist with the provided id")
        })
        @GetMapping("/reviews/{id}")
        public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
                Review review = reviewRepository.findById(id).orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Review with the provided id " + id + " does not exist"));
                return ResponseEntity.ok(review);
        }


        @Operation(summary = "Updates a review with id", description = "Updates review with id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Review updated succesully with the provided id"),
                        @ApiResponse(responseCode = "400", description = "Review does not exist with the provided id")
        })
        @PutMapping("/reviews/edit/{id}")
        public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review updatedReview) {
                Review existingReview = reviewRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Review not found"));

                existingReview.setReviewText(updatedReview.getReviewText());
                existingReview.setRating(updatedReview.getRating());
                existingReview.setReviewerNickname(updatedReview.getReviewerNickname());

                Review savedReview = reviewRepository.save(existingReview);
                return ResponseEntity.ok(savedReview);
        }

        @Operation(summary = "Delete a review by id", description = "Deletes a review with the provided id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Review deleted successfully with the provided id"),
                        @ApiResponse(responseCode = "404", description = "Review not found with the provided id")
        })
        @DeleteMapping("/reviews/{id}")
        public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
                Review review = reviewRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Review with the provided id " + id + " does not exist"));

                reviewRepository.delete(review);
                return ResponseEntity.ok().build();
        }

}

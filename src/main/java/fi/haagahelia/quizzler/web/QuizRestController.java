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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import fi.haagahelia.quizzler.domain.Quiz;
import fi.haagahelia.quizzler.domain.CategoryRepository;
import fi.haagahelia.quizzler.domain.QuestionResultsDTO;
import fi.haagahelia.quizzler.domain.Category;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Quizzes", description = "Operations for retrieving quizzes")
public class QuizRestController {
        @Autowired
        private QuizRepository quizRepository;

        @Autowired
        private CategoryRepository categoryRepository;

        @Operation(summary = "Get a quiz by id", description = "Returns a quiz with the provided id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Quiz with the provided id retrieved succesully"),
                        @ApiResponse(responseCode = "400", description = "Quiz with the provided id does not exist")
        })
        @GetMapping("/quizzes/{id}")
        public Quiz getQuizById(@PathVariable Long id) {
                return quizRepository.findById(id).orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Quiz with the provided id " + id + " does not exist"));
        }

        @Operation(summary = "Get a list of all of the quizzes", description = "Returns a list of all the quizzes")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of quizzes retrieved succesully"),
                        @ApiResponse(responseCode = "400", description = "List of quizzes does not exist")
        })
        @GetMapping("/quizzes")
        public List<Quiz> getAllQuizzes() {
                return quizRepository.findAll();
        }

        @Operation(summary = "Get a list of all of the published quizzes", description = "Returns a list of all the quizzes with published status")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of published quizzes with the provided published status retrieved succesully"),
                        @ApiResponse(responseCode = "400", description = "List of published quizzes with the provided published status does not exist")
        })
        @GetMapping("/quizzes/published")
        public List<Quiz> getPublishedQuizzes() {
                return quizRepository.findAllByPublishedStatus(1);
        }

        @Operation(summary = "Get all the quizzes of a category by category id", description = "Returns a list of a categories' quizzes with the provided category id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of quizzes with the provided id retrieved succesully"),
                        @ApiResponse(responseCode = "400", description = "List of quizzes with the provided id does not exist")
        })
        @GetMapping("/quizzes/categories/{id}")
        public List<Quiz> getQuizzesByCategoryId(@PathVariable Long id) {
                Category category = categoryRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Category with ID " + id + " not found"));

                List<Quiz> quizzes = quizRepository.findByPublishedStatusAndCategory(1, category);

                if (quizzes.isEmpty()) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "No quizzes found for category ID " + id);
                }

                return quizzes;
        }

        @Operation(summary = "Get results of a quiz by quiz id", description = "Returns a stream of a quizzes results with the provided quiz id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Stream of results with the provided id retrieved succesully"),
                        @ApiResponse(responseCode = "400", description = "Stream of results with the provided id does not exist")
        })
        @GetMapping("/quizzes/{id}/results")
        public List<QuestionResultsDTO> getQuizResults(@PathVariable Long id) {
                Quiz quiz = quizRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Quiz with the provided id " + id + " does not exist"));

                return quiz.getQuestions().stream()
                                .map(QuestionResultsDTO::new)
                                .toList();
        }
}

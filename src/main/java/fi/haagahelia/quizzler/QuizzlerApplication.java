package fi.haagahelia.quizzler;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.quizzler.domain.Answers;
import fi.haagahelia.quizzler.domain.AnswersRepository;
import fi.haagahelia.quizzler.domain.Question;
import fi.haagahelia.quizzler.domain.QuestionRepository;
import fi.haagahelia.quizzler.domain.Quiz;
import fi.haagahelia.quizzler.domain.QuizRepository;
import fi.haagahelia.quizzler.domain.Review;
import fi.haagahelia.quizzler.domain.ReviewRepository;
import fi.haagahelia.quizzler.domain.Category;
import fi.haagahelia.quizzler.domain.CategoryRepository;

@SpringBootApplication
public class QuizzlerApplication {
	private static final Logger log = LoggerFactory.getLogger(QuizzlerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(QuizzlerApplication.class, args);
	}

	@Bean
	public CommandLineRunner quizDemo(QuizRepository quizRepo, QuestionRepository questionRepo,
			AnswersRepository answersRepo, CategoryRepository categoryRepo, ReviewRepository reviewRepo) {
		return args -> {
			if (quizRepo.count() == 0) {
				Category category1 = new Category("Agile",
						"Quizzes related to the agile principles and project management frameworks");
				Category category2 = new Category("Databases",
						"Quizzes related to different database management systems and query languages");

				categoryRepo.save(category1);
				categoryRepo.save(category2);

				log.info("Adding some demo data");

				Quiz quiz1 = new Quiz("first quiz", "test", "ABC123", 1, LocalDate.of(2025, 4, 7), category1);
				Quiz quiz2 = new Quiz("second quiz", "test", "ABC567", 1, LocalDate.of(2025, 4, 7), null);
				Quiz quiz3 = new Quiz("third quiz", "test", "ABC890", 0, LocalDate.of(2025, 4, 7), category1);
				Quiz quiz4 = new Quiz("fourth quiz", "test", "ABC123", 1, LocalDate.of(2025, 4, 7), category1);

				quizRepo.save(quiz1);
				quizRepo.save(quiz2);
				quizRepo.save(quiz3);
				quizRepo.save(quiz4);

				Review review1 = new Review("Student 1", 5, "Great quiz!", LocalDate.now());
				review1.setQuiz(quiz1);
				Review review2 = new Review("Student 2", 4, "Very informative.", LocalDate.now());
				review2.setQuiz(quiz1);

				reviewRepo.save(review1);
				reviewRepo.save(review2);

				Question question1 = new Question("What is Java?", "easy", 0, 0);
				question1.setQuiz(quiz1);
				Question question2 = new Question("What is Spring Boot?", "medium", 0, 0);
				question2.setQuiz(quiz1);
				Question question3 = new Question("Explain Dependency Injection", "hard", 0, 0);
				question3.setQuiz(quiz1);

				Question question4 = new Question("What is Java?", "easy", 0, 0);
				question4.setQuiz(quiz2);
				Question question5 = new Question("What is Spring Boot?", "medium", 0, 0);
				question5.setQuiz(quiz2);
				Question question6 = new Question("Explain Dependency Injection", "hard", 0, 0);
				question6.setQuiz(quiz2);

				questionRepo.save(question1);
				questionRepo.save(question2);
				questionRepo.save(question3);
				questionRepo.save(question4);
				questionRepo.save(question5);
				questionRepo.save(question6);

				Answers answer1 = new Answers("A coding language.", 1);
				answer1.setQuestion(question1);
				Answers answer2 = new Answers("A delicious kind of coffee.", 0);
				answer2.setQuestion(question1);

				Answers answer3 = new Answers(
						"Spring Boot is a framework that simplifies the development of Spring applications by providing auto-configuration, embedded servers, and minimal setup.",
						1);
				answer3.setQuestion(question2);
				Answers answer4 = new Answers(
						"Spring Boot is a JavaScript library used for creating dynamic websites in the browser.", 0);
				answer4.setQuestion(question2);

				Answers answer5 = new Answers(
						"Dependency injection is a method of encrypting sensitive data before it's sent over the network.",
						0);
				answer5.setQuestion(question3);
				Answers answer6 = new Answers(
						"Dependency injection is a design pattern where objects receive their dependencies from an external source rather than creating them themselves, promoting loose coupling and easier testing.",
						1);
				answer6.setQuestion(question3);

				answersRepo.save(answer1);
				answersRepo.save(answer2);
				answersRepo.save(answer3);
				answersRepo.save(answer4);
				answersRepo.save(answer5);
				answersRepo.save(answer6);

				log.info("All done!");

			}
		};
	}
}

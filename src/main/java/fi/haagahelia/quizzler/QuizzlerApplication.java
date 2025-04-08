package fi.haagahelia.quizzler;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.quizzler.domain.Question;
import fi.haagahelia.quizzler.domain.QuestionRepository;
import fi.haagahelia.quizzler.domain.Quiz;
import fi.haagahelia.quizzler.domain.QuizRepository;

@SpringBootApplication
public class QuizzlerApplication {
	private static final Logger log = LoggerFactory.getLogger(QuizzlerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(QuizzlerApplication.class, args);
	}

	@Bean
	public CommandLineRunner quizDemo(QuizRepository quizRepo, QuestionRepository questionRepo) {
		return args -> {
			log.info("Adding some demo data");
			Quiz quiz1 = new Quiz("first quiz", "test", "ABC123", 1, LocalDate.of(2025, 4, 7));
			Quiz quiz2 = new Quiz("second quiz", "test", "ABC567", 1, LocalDate.of(2025, 4, 7));
			Quiz quiz3 = new Quiz("third quiz", "test", "ABC890", 1, LocalDate.of(2025, 4, 7));
			Quiz quiz4 = new Quiz("fourth quiz", "test", "ABC123", 1, LocalDate.of(2025, 4, 7));

			quizRepo.save(quiz1);
			quizRepo.save(quiz2);
			quizRepo.save(quiz3);
			quizRepo.save(quiz4);

			Question question1 = new Question("What is Java?", "easy");
			question1.setQuiz(quiz1);
			Question question2 = new Question("What is Spring Boot?", "medium");
			question2.setQuiz(quiz1);
			Question question3 = new Question("Explain Dependency Injection", "hard");
			question3.setQuiz(quiz1);

			questionRepo.save(question1);
			questionRepo.save(question2);
			questionRepo.save(question3);

		};
	}

}

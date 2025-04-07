package fi.haagahelia.quizzler;


import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.quizzler.domain.Quiz;
import fi.haagahelia.quizzler.domain.QuizRepository;

@SpringBootApplication
public class QuizzlerApplication {
	private static final Logger log = LoggerFactory.getLogger(QuizzlerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(QuizzlerApplication.class, args);
	}

	@Bean
	public CommandLineRunner quizDemo(QuizRepository quizRepo){
		return args -> {
			log.info("Adding some demo data");
			quizRepo.save(new Quiz("first quiz", "test", "ABC123", 1,LocalDate.of(2025, 4, 7)));
			quizRepo.save(new Quiz("second quiz", "test", "ABC567", 1,LocalDate.of(2025, 4, 7)));
			quizRepo.save(new Quiz("third quiz", "test", "ABC8910", 1,LocalDate.of(2025, 4, 7)));
			quizRepo.save(new Quiz("fourth quiz", "test", "ABC111213", 1,LocalDate.of(2025, 4, 7)));
		};
	}

}

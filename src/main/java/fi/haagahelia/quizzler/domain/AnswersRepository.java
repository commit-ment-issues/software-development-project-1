package fi.haagahelia.quizzler.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswersRepository extends JpaRepository<Answers, Long> {
    Answers findByQuestionQuestionId(Long questionId);

}


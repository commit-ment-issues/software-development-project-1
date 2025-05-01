package fi.haagahelia.quizzler.domain;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswersRepository extends JpaRepository<Answers, Long> {
    List<Answers> findByQuestionQuestionId(Long questionId);

}


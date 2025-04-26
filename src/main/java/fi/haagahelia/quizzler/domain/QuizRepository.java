package fi.haagahelia.quizzler.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    public List<Quiz> findAllByPublishedStatus(Integer status);
    public List<Quiz> findByCategory(Category category);
}

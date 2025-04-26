package fi.haagahelia.quizzler.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Categoryid;
    private String name;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Quiz> quizzes; 

    public Category() {
    }

    public Category(String name, String description, List<Quiz> quizzes) {
        this.name = name;
        this.description = description;
        this.quizzes = quizzes;
    }

    public Long getCategoryid() {
        return Categoryid;
    }

    public void setCategoryid(Long categoryid) {
        Categoryid = categoryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    
    
}

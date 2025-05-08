package fi.haagahelia.quizzler.domain;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizId;

    private String name;
    private String description;
    private String courseCode;
    private Integer publishedStatus;
    private LocalDate creationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quiz")
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quiz")
    private List<Review> reviews;

    public Quiz() {
    }

    public Quiz(String name, String description, String courseCode, Integer publishedStatus, LocalDate creationDate,
            Category category) {
        this.name = name;
        this.description = description;
        this.courseCode = courseCode;
        this.publishedStatus = publishedStatus;
        this.creationDate = creationDate;
        this.category = category;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
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

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Integer getPublishedStatus() {
        return publishedStatus;
    }

    public void setPublishedStatus(Integer publishedStatus) {
        this.publishedStatus = publishedStatus;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

}
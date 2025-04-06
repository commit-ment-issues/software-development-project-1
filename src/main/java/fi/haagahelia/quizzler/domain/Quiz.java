package fi.haagahelia.quizzler.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String courseCode;
    private Integer publishedStatus;
    private LocalDate creationDate;
    
    public Quiz() {
    }

    public Quiz(String name, String description, String courseCode, Integer publishedStatus, LocalDate creationDate) {
        this.name = name;
        this.description = description;
        this.courseCode = courseCode;
        this.publishedStatus = publishedStatus;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
}

package fi.haagahelia.quizzler.domain;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reviewId;

    private String reviewerNickname;
    private int rating;
    private String reviewText;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "quizId")
    private Quiz quiz;

    public Review() {
    }

    public Review(String reviewerNickname, int rating, String reviewText) {
        this.reviewerNickname = reviewerNickname;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewerNickname() {
        return reviewerNickname;
    }

    public void setReviewerNickname(String reviewerNickname) {
        this.reviewerNickname = reviewerNickname;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    


}

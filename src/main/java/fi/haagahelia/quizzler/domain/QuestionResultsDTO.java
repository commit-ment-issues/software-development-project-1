package fi.haagahelia.quizzler.domain;

import java.util.List;

public class QuestionResultsDTO {
    private Long questionId;
    private String questionText;
    private String difficulty;
    private Integer totalAnswers;
    private Integer correctAnswers;
    private List<AnswerDTO> answers;

    public QuestionResultsDTO() {
    }

    public QuestionResultsDTO(Question question) {
        this.questionId = question.getQuestionId();
        this.questionText = question.getQuestionText();
        this.difficulty = question.getDifficulty();
        this.totalAnswers = question.getTotalAnswers();
        this.correctAnswers = question.getCorrectAnswers();
        this.answers = question.getAnswers().stream()
                .map(answer -> new AnswerDTO(
                        answer.getId(),
                        answer.getText(),
                        answer.getStatus()))
                .toList();
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers(Integer totalAnswers) {
        this.totalAnswers = totalAnswers;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }
}

package fi.haagahelia.quizzler.domain;

public class QuestionResultsDTO {

    private Long questionId;
    private String questionText;
    private String questionDifficulty;
    private int totalAnswers;
    private int correctAnswers;
    private int wrongAnswers;

    public QuestionResultsDTO() {
    }

    public QuestionResultsDTO(Long questionId, String questionText, String questionDifficulty, int totalAnswers,
            int correctAnswers, int wrongAnswers) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionDifficulty = questionDifficulty;
        this.totalAnswers = totalAnswers;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
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

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public int getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers(int totalAnswers) {
        this.totalAnswers = totalAnswers;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    

}

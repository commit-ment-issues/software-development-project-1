package fi.haagahelia.quizzler.domain;

public class CreateAnswerDTO {

    private Long answerOptionId;

    public CreateAnswerDTO() {
    }

    public CreateAnswerDTO(Long answerOptionId) {
        this.answerOptionId = answerOptionId;
    }

    public Long getAnswerOptionId() {
        return answerOptionId;
    }

    public void setAnswerOptionId(Long answerOptionId) {
        this.answerOptionId = answerOptionId;
    }

    
}

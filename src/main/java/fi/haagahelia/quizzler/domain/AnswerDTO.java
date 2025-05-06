package fi.haagahelia.quizzler.domain;

public class AnswerDTO {

    private Long id;
    private String text;
    private Integer status;

    public AnswerDTO(Long id, String text, Integer status) {
        this.id = id;
        this.text = text;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}

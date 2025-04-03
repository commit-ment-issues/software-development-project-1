package domain;

public class Question {
    private long id;
    private String question, difficulty;

    public Question( String question, String difficulty) {
        this.question = question;
        this.difficulty = difficulty;
    }

    public long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }

}

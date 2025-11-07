package jwp.model;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "QUESTIONS")
@NoArgsConstructor
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // Id 자동 할당
    private int questionId;
    private String writer;
    private String title;
    private String contents;
    private Date createdDate;
    private int countOfAnswer;

    public Question(int questionId, String writer, String title, String contents, Date createdDate, int countOfAnswer) {
        System.out.println("사용된 생성자1");
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfAnswer = countOfAnswer;
    }

    public Question(String writer, String title, String contents) {
        System.out.println("사용된 생성자2");
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = Date.valueOf(LocalDate.now());
        this.countOfAnswer = 0;
    }

    // Id 자동 할당
    public Question(String writer, String title, String contents, int countOfAnswer) {
        System.out.println("사용된 생성자3");
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = Date.valueOf(LocalDate.now());
        this.countOfAnswer = countOfAnswer;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = Date.valueOf(LocalDate.now());
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public int getCountOfAnswer() {
        return countOfAnswer;
    }

    public void updateTitleAndContents(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void increaseCountOfAnswer() {
        countOfAnswer += 1;
    }

    public void decreaseCountOfAnswer() {
        countOfAnswer -= 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return getCountOfAnswer() == question.getCountOfAnswer() && Objects.equals(getWriter(), question.getWriter()) && Objects.equals(getTitle(), question.getTitle()) && Objects.equals(getContents(), question.getContents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWriter(), getTitle(), getContents(), getCountOfAnswer());
    }

    public boolean isSameUser(User user) {
        if (user == null) return false;
        return writer.equals(user.getUserId());
    }
}
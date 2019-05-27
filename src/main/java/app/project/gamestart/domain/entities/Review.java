package app.project.gamestart.domain.entities;

import app.project.gamestart.util.constants.ReviewConstants;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity{

    private String title;
    private String text;
    private boolean recommended;
    private LocalDate submissionDate;
    private User user;
    private Book book;


    public Review() {
    }

    @Column(nullable = false)
    @Length(min = 1, max = ReviewConstants.TITLE_MAX_LENGTH,message = ReviewConstants.TITLE_LENGTH_ERROR_MESSAGE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false)
    @Length(min = 1, max = ReviewConstants.TEXT_MAX_LENGTH, message = ReviewConstants.TEXT_LENGTH_ERROR_MESSAGE)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public Book getBook() {
        return book;
    }


    public void setBook(Book book) {
        this.book = book;
    }

    @Column(nullable = false)
    public boolean getRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    @Column(nullable = false)
    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }
}

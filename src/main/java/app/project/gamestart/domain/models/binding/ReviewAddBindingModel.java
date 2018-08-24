package app.project.gamestart.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class ReviewAddBindingModel {
    private String title;
    private String text;
    private boolean recommended;

    public ReviewAddBindingModel() {
    }

    @NotEmpty
    @Length(max = 50,message = "Max 50 characters")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotEmpty
    @Length(max = 1000, message = "Max 1000 characters")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }
}

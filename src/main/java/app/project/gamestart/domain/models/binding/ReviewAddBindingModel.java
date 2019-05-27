package app.project.gamestart.domain.models.binding;

import app.project.gamestart.util.constants.ReviewConstants;
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
    @Length(max = ReviewConstants.TITLE_MAX_LENGTH,message = ReviewConstants.TITLE_LENGTH_ERROR_MESSAGE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotEmpty
    @Length(max = ReviewConstants.TEXT_MAX_LENGTH, message = ReviewConstants.TEXT_LENGTH_ERROR_MESSAGE)
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

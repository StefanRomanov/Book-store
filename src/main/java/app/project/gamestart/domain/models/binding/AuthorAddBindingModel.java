package app.project.gamestart.domain.models.binding;

import app.project.gamestart.util.constants.GlobalConstants;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AuthorAddBindingModel {
    private static final String AUTHOR_EMPTY_ERROR_MESSAGE = "Please enter author name";
    private static final int AUTHOR_MIN_CHARACTERS = 3;
    private static final int AUTHOR_MAX_CHARACTERS = 25;
    private static final String AUTHOR_LENGTH_ERROR_MESSAGE = "Name should be between 3 and 25 characters long";
    private static final String DATE_NULL_ERROR_MESSAGE = "Please enter date of birth";
    private static final String COUNTRY_NULL_ERROR_MESSAGE = "Please enter country of origin";

    private String name;
    private LocalDate dateOfBirth;
    private String country;

    public AuthorAddBindingModel() {
    }

    @NotEmpty(message = AUTHOR_EMPTY_ERROR_MESSAGE)
    @Length(min = AUTHOR_MIN_CHARACTERS ,max = AUTHOR_MAX_CHARACTERS, message = AUTHOR_LENGTH_ERROR_MESSAGE)
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = DATE_NULL_ERROR_MESSAGE)
    @DateTimeFormat(pattern = GlobalConstants.DATE_FORMAT)
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @NotEmpty(message = COUNTRY_NULL_ERROR_MESSAGE)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

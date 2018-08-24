package app.project.gamestart.domain.models.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AuthorAddBindingModel {
    private String name;
    private LocalDate dateOfBirth;
    private String country;

    public AuthorAddBindingModel() {
    }

    @NotEmpty(message = "Please enter author name")
    @Length(min = 3 ,max = 25, message = "Name should be between 3 and 25 characters long")
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Please enter date of birth")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @NotEmpty(message = "Please enter country of origin")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

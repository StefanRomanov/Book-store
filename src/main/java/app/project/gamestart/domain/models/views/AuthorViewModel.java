package app.project.gamestart.domain.models.views;

import app.project.gamestart.domain.enums.Country;

import java.time.LocalDate;

public class AuthorViewModel {
    String id;
    private String name;
    private String dateOfBirth;
    private String country;

    public AuthorViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

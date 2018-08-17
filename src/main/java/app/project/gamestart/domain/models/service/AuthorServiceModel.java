package app.project.gamestart.domain.models.service;

import app.project.gamestart.domain.enums.Country;

import java.time.LocalDate;

public class AuthorServiceModel {

    private String id;
    private String name;
    private LocalDate dateOfBirth;
    private Country country;

    public AuthorServiceModel() {
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}

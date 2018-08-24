package app.project.gamestart.domain.entities;

import app.project.gamestart.domain.enums.Country;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "authors")
public class Author extends BaseEntity {

    private String name;
    private LocalDate dateOfBirth;
    private Country country;
    private Set<Book> books;


    public Author() {
        this.books = new HashSet<>();
    }

    @Column(nullable = false)
    @Length(min = 3, max = 25)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Enumerated(value = EnumType.STRING)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @ManyToMany(mappedBy = "authors")
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }


}

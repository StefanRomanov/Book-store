package app.project.gamestart.domain.entities;

import app.project.gamestart.domain.enums.Genre;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Book extends BaseEntity{

    private String title;
    private Author author;
    private Set<Review> reviews;
    private Boolean approved;
    private Set<Genre> genres;
    private String description;
    private BigDecimal price;
    private String coverImageUrl;
    private String textFile;
    private LocalDate releaseDate;
    private Set<User> users;

    public Book() {
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @OneToMany(mappedBy = "book")
    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    @Column(nullable = false)
    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    @Column
    @Lob
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(nullable = false)
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Column(nullable = false)
    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    @Column(nullable = false)
    public String getTextFile() {
        return textFile;
    }

    public void setTextFile(String textFile) {
        this.textFile = textFile;
    }

    @ElementCollection(targetClass = Genre.class)
    @CollectionTable(name = "genres", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    @ManyToMany(mappedBy = "books")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    private Integer reviewScore(){
        int approved = this.getReviews().stream().filter(Review::getApproved).collect(Collectors.toList()).size();
        int total = this.getReviews().size();

        return (approved * total) / 100;
    }
}

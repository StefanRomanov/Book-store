package app.project.gamestart.domain.models.service;

import app.project.gamestart.domain.entities.Author;
import app.project.gamestart.domain.entities.Review;
import app.project.gamestart.domain.enums.Genre;
import app.project.gamestart.domain.enums.PegiRatings;
import app.project.gamestart.domain.enums.Platform;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BookServiceModel {

    private String id;
    private String title;
    private Set<Author> authors;
    private Set<Review> reviews;
    private Boolean approved;
    private Set<Genre> genres;
    private String description;
    private BigDecimal price;
    private String coverImageUrl;
    private Set<String> images;
    private LocalDate releaseDate;

    public BookServiceModel() {
        this.images = new HashSet<>();
        this.genres = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer reviewScore(){
        int approved = this.getReviews().stream().filter(Review::getApproved).collect(Collectors.toList()).size();
        int total = this.getReviews().size();
        if(total == 0){
            return 100;
        }

        return (approved * total) / 100;
    }
}

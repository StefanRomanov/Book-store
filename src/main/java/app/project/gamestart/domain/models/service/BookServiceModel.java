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

public class BookServiceModel {
    private String title;
    private Platform platform;
    private Author author;
    private Set<Review> reviews;
    private Boolean approved;
    private Set<Genre> genres;
    private PegiRatings pegiRating;
    private String description;
    private BigDecimal price;
    private String coverImageUrl;
    private Set<String> images;
    private LocalDate releaseDate;

    public BookServiceModel() {
        this.images = new HashSet<>();
        this.genres = new HashSet<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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

    public PegiRatings getPegiRating() {
        return pegiRating;
    }

    public void setPegiRating(PegiRatings pegiRating) {
        this.pegiRating = pegiRating;
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
}

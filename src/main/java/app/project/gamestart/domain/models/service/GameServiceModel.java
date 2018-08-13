package app.project.gamestart.domain.models.service;

import app.project.gamestart.domain.entities.Developer;
import app.project.gamestart.domain.entities.Review;
import app.project.gamestart.domain.enums.GenreName;
import app.project.gamestart.domain.enums.PegiRatings;
import app.project.gamestart.domain.enums.Platform;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class GameServiceModel {
    private String title;
    private Platform platform;
    private Developer developer;
    private Set<Review> reviews;
    private Boolean approved;
    private Set<GenreName> genres;
    private PegiRatings pegiRating;
    private String description;
    private BigDecimal price;
    private String coverImageUrl;
    private Set<String> images;
    private LocalDate releaseDate;

    public GameServiceModel() {
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

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
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

    public Set<GenreName> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreName> genres) {
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

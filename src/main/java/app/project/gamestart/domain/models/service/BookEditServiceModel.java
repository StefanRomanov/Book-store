package app.project.gamestart.domain.models.service;

import app.project.gamestart.domain.enums.Genre;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BookEditServiceModel {
    private String genre;
    private String description;
    private BigDecimal price;
    private File coverImageUrl;
    private LocalDate releaseDate;

    public BookEditServiceModel() {
    }


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public File getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(File coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}

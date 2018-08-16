package app.project.gamestart.domain.models.binding;

import app.project.gamestart.domain.entities.Author;
import app.project.gamestart.domain.entities.Review;
import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.enums.Genre;
import app.project.gamestart.validators.annotations.NotEmptyFile;
import java.io.File;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class BookAddBindingModel {

    private String title;
    private String author;
    private Set<Review> reviews;
    private Boolean approved;
    private Set<String> genres;
    private String description;
    private BigDecimal price;
    private MultipartFile coverImageUrl;
    private MultipartFile textFile;
    private String releaseDate;
    private Set<User> users;
    private File cover;
    private File text;

    public BookAddBindingModel() {
        this.genres = new HashSet<>();
    }


    @NotEmpty
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public MultipartFile getTextFile() {
        return textFile;
    }

    public void setTextFile(MultipartFile textFile) {
        this.textFile = textFile;
    }

    @NotNull
    @Size(min = 1,message = "Select at least one genre !")
    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Books should have price!")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull
    @NotEmptyFile
    public MultipartFile getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(MultipartFile coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    @NotEmpty
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public File getFile() {
        return cover;
    }

    public void setFile(File file) {
        this.cover = file;
    }

    public File getText() {
        return text;
    }

    public void setText(File text) {
        this.text = text;
    }
}

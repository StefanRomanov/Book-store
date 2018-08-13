package app.project.gamestart.domain.models.binding;

import app.project.gamestart.validators.annotations.NotEmptyFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class GameAddBindingModel {

    private String title;
    private String platform;
    private String developer;
    private Set<String> genres;
    private String pegiRating;
    private String description;
    private BigDecimal price;
    private MultipartFile coverImageUrl;
    private Set<MultipartFile> images;
    private String releaseDate;

    public GameAddBindingModel() {
        this.genres = new HashSet<>();
    }

    @NotEmpty
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotEmpty(message = "Pick a platform !")
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    @NotNull
    @Size(min = 1,message = "Select at least one genre !")
    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    @NotEmpty
    public String getPegiRating() {
        return pegiRating;
    }

    public void setPegiRating(String pegiRating) {
        this.pegiRating = pegiRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Games should have price!")
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

    public Set<MultipartFile> getImages() {
        return images;
    }

    public void setImages(Set<MultipartFile> images) {
        this.images = images;
    }

    @NotEmpty
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}

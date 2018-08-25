package app.project.gamestart.domain.models.binding;

import app.project.gamestart.util.constants.BookConstants;
import app.project.gamestart.util.constants.GlobalConstants;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BookEditBindingModel {

    private String id;
    private String genre;
    private String description;
    private BigDecimal price;
    private MultipartFile coverImageUrl;
    private LocalDate releaseDate;

    public BookEditBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotEmpty(message = BookConstants.NO_GENRE_SELECTED_ERROR_MESSAGE)
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Length(max = BookConstants.BOOK_DESCRIPTION_MAX_CHARACTERS, message = BookConstants.BOOK_DESCRIPTION_CHARACTERS_COUNT_ERROR_MESSAGE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = BookConstants.NO_PRICE_ENTERED_MESSAGE)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MultipartFile getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(MultipartFile coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    @NotNull
    @DateTimeFormat(pattern = GlobalConstants.DATE_FORMAT)
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}

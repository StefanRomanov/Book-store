package app.project.gamestart.domain.models.binding;

import app.project.gamestart.util.constants.BookConstants;
import app.project.gamestart.util.constants.GlobalConstants;
import app.project.gamestart.domain.models.views.AuthorViewModel;
import app.project.gamestart.web.validators.annotations.FileSizeValidation;
import app.project.gamestart.web.validators.annotations.NotEmptyFile;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


public class BookAddBindingModel {


    private String title;
    private Set<String> authors;
    private String genre;
    private String description;
    private BigDecimal price;
    private MultipartFile coverImageUrl;
    private MultipartFile textFile;
    private LocalDate releaseDate;
    private List<AuthorViewModel> authorViews;

    public BookAddBindingModel(){
    }


    @NotEmpty
    @Length(max = BookConstants.BOOK_TITLE_MAX_CHARACTERS, message = BookConstants.BOOK_TITLE_MAX_CHARACTERS_ERROR_MESSAGE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    @NotEmptyFile
    @FileSizeValidation
    public MultipartFile getTextFile() {
        return textFile;
    }

    public void setTextFile(MultipartFile textFile) {
        this.textFile = textFile;
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

    @NotNull
    @NotEmptyFile
    @FileSizeValidation
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
    @NotNull
    @Size(min = 1,message = BookConstants.NO_AUTHOR_SELECTED_ERROR_MESSAGE)
    public Set<String> getAuthors() {
        return authors;
    }


    public void setAuthors(Set<String> authors) {
        this.authors = authors;
    }

    public List<AuthorViewModel> getAuthorViews() {
        return authorViews;
    }

    public void setAuthorViews(List<AuthorViewModel> authorViews) {
        this.authorViews = authorViews;
    }
}

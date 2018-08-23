package app.project.gamestart.domain.models.binding;

import app.project.gamestart.domain.models.views.AuthorViewModel;
import app.project.gamestart.validators.annotations.FileSizeValidation;
import app.project.gamestart.validators.annotations.NotEmptyFile;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
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

    @NotEmpty(message = "Select genre !")
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Length(max = 2000, message = "Maximum 2000 characters")
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
    @FileSizeValidation
    public MultipartFile getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(MultipartFile coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    @NotNull
    @Size(min = 1,message = "Select at least one author !")
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

package app.project.gamestart.domain.models.service;

import app.project.gamestart.domain.entities.Author;
import app.project.gamestart.domain.entities.Publisher;
import app.project.gamestart.domain.entities.Review;
import app.project.gamestart.domain.enums.Genre;
import app.project.gamestart.util.constants.BookConstants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class BookServiceModel {

    private String id;
    private String title;
    private Set<Author> authors;
    private Set<Review> reviews;
    private Publisher publisher;
    private Boolean approved;
    private Genre genre;
    private String description;
    private BigDecimal price;
    private String coverImageUrl;
    private String textFile;
    private LocalDate releaseDate;

    public BookServiceModel() {
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genres) {
        this.genre = genres;
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

    public String getTextFile() {
        return textFile;
    }

    public void setTextFile(String textFile) {
        this.textFile = textFile;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String reviewScore(){
        double approved = (double) this.getReviews().stream().filter(Review::getRecommended).collect(Collectors.toList()).size();

        double total = (double) this.getReviews().size();

        if(total == 0){
            return BookConstants.NO_REVIEWS_MESSAGE;
        }
        Double result = (approved / total) * 100.0;


        return String.format("%.2f",result) + "%";
    }
}

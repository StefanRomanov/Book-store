package app.project.gamestart.domain.entities;

import app.project.gamestart.domain.enums.GenreName;
import app.project.gamestart.domain.enums.PegiRatings;
import app.project.gamestart.domain.enums.Platform;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "games")
public class Game extends BaseEntity{

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

    public Game() {
    }

    @Column(nullable = false)
    public String getName() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    @ManyToOne
    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    @OneToMany(mappedBy = "game")
    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    @Column(nullable = false)
    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    @Column
    @Lob
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    public PegiRatings getPegiRating() {
        return pegiRating;
    }

    public void setPegiRating(PegiRatings pegiRating) {
        this.pegiRating = pegiRating;
    }

    @Column(nullable = false)
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Column(nullable = false)
    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    @ElementCollection
    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    @ElementCollection(targetClass = GenreName.class)
    @CollectionTable(name = "genres", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    public Set<GenreName> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreName> genres) {
        this.genres = genres;
    }

    private Integer reviewScore(){
        int approved = this.getReviews().stream().filter(Review::getApproved).collect(Collectors.toList()).size();
        int total = this.getReviews().size();

        return (approved * total) / 100;
    }
}

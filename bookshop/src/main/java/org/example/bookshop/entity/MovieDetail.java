package org.example.bookshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "MOVIE_DETAILS")
public class MovieDetail {
    @Id
    @Column(name = "MOVIE_ID")
    private Long movieID;

    @OneToOne
    @MapsId
    @JoinColumn(name = "MOVIE_ID")
    private Product product;

    @Column(name = "DIRECTOR")
    private String director;

    @Column(name = "DURATION_IN_MINUTES")
    private Integer durationInMinutes;

    @Column(name = "RELEASE_YEAR")
    private Integer releaseYear;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "GENRE")
    private String genre;

    @Column(name = "DESCRIPTION")
    private String description;

    public Long getMovieID() {
        return movieID;
    }

    public void setMovieID(Long movieID) {
        this.movieID = movieID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
}

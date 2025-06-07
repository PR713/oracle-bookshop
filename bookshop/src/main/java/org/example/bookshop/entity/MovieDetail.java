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
}

package org.example.bookshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "MovieDetails")
public class MovieDetail {
    @Id
    @Column(name = "MovieID")
    private Long movieID;

    @OneToOne
    @JoinColumn(name = "MovieID")
    private Product product;

    @Column(name = "Director")
    private String director;

    @Column(name = "DurationInMinutes")
    private Integer durationInMinutes;

    @Column(name = "ReleaseYear")
    private Integer releaseYear;

    @Column(name = "Language")
    private String language;

    @Column(name = "Genre")
    private String genre;

    @Column(name = "Description")
    private String description;
}

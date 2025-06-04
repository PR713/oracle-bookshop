package org.example.bookshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "GameDetails")
public class GameDetail {
    @Id
    @Column(name = "GameID")
    private Long gameID;

    @OneToOne
    @JoinColumn(name = "GameID")
    private Product product;

    @Column(name = "Platform")
    private String platform;

    @Column(name = "Developer")
    private String developer;

    @Column(name = "ReleaseYear")
    private Integer releaseYear;

    @Column(name = "Description")
    private String description;
}

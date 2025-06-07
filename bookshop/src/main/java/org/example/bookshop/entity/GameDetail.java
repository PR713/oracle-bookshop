package org.example.bookshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "GAME_DETAILS")
public class GameDetail {
    @Id
    @Column(name = "GAME_ID")
    private Long gameID;

    @OneToOne
    @MapsId
    @JoinColumn(name = "GAME_ID")
    private Product product;

    @Column(name = "PLATFORM")
    private String platform;

    @Column(name = "DEVELOPER")
    private String developer;

    @Column(name = "RELEASE_YEAR")
    private Integer releaseYear;

    @Column(name = "DESCRIPTION")
    private String description;
}

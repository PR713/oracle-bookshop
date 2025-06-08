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

    public Long getGameID() {
        return gameID;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

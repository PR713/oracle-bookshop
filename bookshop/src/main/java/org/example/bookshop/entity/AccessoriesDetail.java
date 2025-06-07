package org.example.bookshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ACCESSORIES_DETAILS")
public class AccessoriesDetail {
    @Id
    @Column(name = "ACCESSORY_ID")
    private Long accessoryID;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ACCESSORY_ID")
    private Product product;

    @Column(name = "DESCRIPTION")
    private String description;
}


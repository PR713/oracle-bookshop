package org.example.bookshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "AccessoriesDetails")
public class AccessoriesDetail {
    @Id
    @Column(name = "AccessoryID")
    private Long accessoryID;

    @OneToOne
    @JoinColumn(name = "AccessoryID")
    private Product product;

    @Column(name = "Description")
    private String description;
}


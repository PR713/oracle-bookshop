package org.example.bookshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @Column(name = "ProductID")
    private Long id;

    @Column(name = "ProductName")
    private String name;

    @Column(name = "Price")
    private Float price;

    @Column(name = "StockQuantity")
    private Integer stock;

    @Column(name = "Rating")
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private Category category;


    //gettery settery konstruktor
}

package org.example.bookshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    @Column(name = "ProductID")
    private Long productId;

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


    public Long getProductID() {
        return productId;
    }

    public void setProductID(Long productID) {
        this.productId = productID;
    }

    public Integer getStock() {return stock;}

    public void setStock(Integer stock) {this.stock = stock;}
}

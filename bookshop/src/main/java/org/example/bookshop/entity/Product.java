package org.example.bookshop.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT_NAME")
    private String name;

    @Column(name = "PRICE")
    private Float price;

    @Column(name = "STOCK_QUANTITY")
    private Integer stock;

    @Column(name = "RATING")
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;


    public Long getProductID() {
        return productId;
    }

    public void setProductID(Long productID) {
        this.productId = productID;
    }

    public Integer getStock() {return stock;}

    public void setStock(Integer stock) {this.stock = stock;}

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

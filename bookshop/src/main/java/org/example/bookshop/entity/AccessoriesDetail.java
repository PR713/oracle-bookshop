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

    public Long getAccessoryID() {
        return accessoryID;
    }

    public void setAccessoryID(Long accessoryID) {
        this.accessoryID = accessoryID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


package org.example.bookshop.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class OrderDetailId implements Serializable {
    @Column(name = "ORDER_ID")
    private Long orderID;

    @Column(name = "PRODUCT_ID")
    private Long productID;
}

package org.example.bookshop.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class OrderDetailId implements Serializable {
    @Column(name = "OrderID")
    private Long orderID;

    @Column(name = "ProductID")
    private Long productID;
}

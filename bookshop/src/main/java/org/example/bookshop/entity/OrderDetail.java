package org.example.bookshop.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "OrderDetails")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId id;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "UnitPrice")
    private BigDecimal unitPrice;

    @Column(name = "Discount")
    private BigDecimal discount;

    @ManyToOne
    @MapsId("orderID")
    @JoinColumn(name = "OrderID")
    private Order order;

    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "ProductID")
    private Product product;
}

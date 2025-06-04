package org.example.bookshop.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @Column(name = "OrderID")
    private Long orderID;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    @Column(name = "OrderDate")
    private LocalDate orderDate;

    @Column(name = "OrderStatus")
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "ShipVia")
    private Shipper shipVia;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order")
    private List<Payment> payments;
}
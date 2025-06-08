package org.example.bookshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "ORDER_SEQ", allocationSize = 1)
    @Column(name = "ORDER_ID")
    private Long orderID;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Column(name = "ORDER_DATE")
    private LocalDate orderDate;

    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "SHIP_VIA")
    private Shipper shipVia;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order")
    private List<Payment> payments;

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setShipVia(Shipper shipper) {
        this.shipVia = shipper;
    }

    public Shipper getShipVia() {
        return this.shipVia;
    }


    public @NotNull(message = "Order date is required") LocalDate getOrderDate() {
        return this.orderDate;
    }

    public Customer getCustomer() {
        return this.customer;
    }


    public @NotBlank(message = "Order status is required") @Pattern(regexp = "NEW|CANCELLED|COMPLETED|PROCESSING",
            message = "Order status must be NEW, CANCELLED, COMPLETED, or PROCESSING") String getOrderStatus() {
        return this.orderStatus;
    }
}
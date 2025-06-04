package org.example.bookshop.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Payments")
public class Payment {
    @Id
    @Column(name = "PaymentID")
    private Long paymentID;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    private Order order;

    @Column(name = "PaymentDate")
    private LocalDate paymentDate;

    @Column(name = "PaymentStatus")
    private String paymentStatus;

    public Long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }

}
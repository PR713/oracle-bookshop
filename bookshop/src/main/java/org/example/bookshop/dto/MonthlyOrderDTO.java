package org.example.bookshop.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MonthlyOrderDTO {
    private Long orderId;
    private String customerName;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String orderStatus;

    public MonthlyOrderDTO(Long orderId, String customerName, LocalDate orderDate,
                           BigDecimal totalAmount, String orderStatus) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
}
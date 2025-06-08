package org.example.bookshop.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MonthlyOrderDTO {
    private Long orderId;
    private String customerName;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String orderStatus;

    public MonthlyOrderDTO(Long orderId, String customerName, LocalDateTime orderDate,
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

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
}
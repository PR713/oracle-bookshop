package org.example.bookshop.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RecentOrderDTO {
    private Long orderId;
    private String customerName;
    private LocalDateTime orderDate;
    private BigDecimal amount;
    private String orderStatus;

    public RecentOrderDTO(Long orderId, String customerName, LocalDateTime orderDate,
                          BigDecimal amount, String orderStatus) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.amount = amount;
        this.orderStatus = orderStatus;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
}
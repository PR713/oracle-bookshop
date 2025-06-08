package org.example.bookshop.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PurchaseItemDTO {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Discount is required")
    @DecimalMin(value = "0.0", message = "Discount cannot be negative")
    @DecimalMax(value = "1.0", message = "Discount cannot exceed 100%")
    private Float discount;


    public PurchaseItemDTO() {}

    public PurchaseItemDTO(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getDiscount() {
        return discount != null ? discount : 0.0f;
    }

    @Override
    public String toString() {
        return "PurchaseItemDTO{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
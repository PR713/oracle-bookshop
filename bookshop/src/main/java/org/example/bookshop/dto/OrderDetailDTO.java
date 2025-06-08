package org.example.bookshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrderDetailDTO {
    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.01", message = "Unit price must be at least 0.01")
    private Float unitPrice;

    @NotNull(message = "Discount is required")
    @DecimalMin(value = "0.0", message = "Discount cannot be negative")
    @DecimalMax(value = "1.0", message = "Discount cannot exceed 1.0")
    private Float discount;
}

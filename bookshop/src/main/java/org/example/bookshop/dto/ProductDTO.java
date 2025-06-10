package org.example.bookshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductDTO {
    private Long productId; // Optional for new products

    @NotBlank(message = "Product name is required")
    @Size(max = 50, message = "Product name cannot exceed 50 characters")
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private Float price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stock;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private Integer rating;

    private Long categoryId; // Will be automatically set to Movie category

    public ProductDTO() {}

    public ProductDTO(Long productId, String name, Float price, Integer stock, Integer rating, Long categoryId) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.rating = rating;
        this.categoryId = categoryId;
    }
}
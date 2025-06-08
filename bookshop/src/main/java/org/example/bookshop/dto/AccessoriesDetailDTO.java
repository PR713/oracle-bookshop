package org.example.bookshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AccessoriesDetailDTO {
    @NotNull(message = "Accessory ID is required")
    private Long accessoryId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotBlank(message = "Description is required")
    @Size(max = 100, message = "Description cannot exceed 100 characters")
    private String description;
}
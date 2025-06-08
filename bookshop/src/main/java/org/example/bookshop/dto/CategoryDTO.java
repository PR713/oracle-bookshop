package org.example.bookshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CategoryDTO {
    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotBlank(message = "Category name is required")
    @Size(max = 50, message = "Category name cannot exceed 50 characters")
    private String categoryName;
}
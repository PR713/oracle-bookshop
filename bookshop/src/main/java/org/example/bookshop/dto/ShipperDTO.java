package org.example.bookshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ShipperDTO {
    private Long shipperId;

    @NotBlank(message = "Company name is required")
    @Size(max = 100, message = "Company name cannot exceed 100 characters")
    private String companyName;

    @NotNull(message = "Phone is required")
    private Long phone;
}
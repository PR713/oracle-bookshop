package org.example.bookshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Map;

@Data
public class PurchaseRequestDTO {
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Product quantities are required")
    @Size(min = 1, message = "At least one product must be specified")
    private Map<Long, Integer> productQuantities;
}
package org.example.bookshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class OrderDTO {
    private Long orderId;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Order date is required")
    private LocalDate orderDate;

    @NotBlank(message = "Order status is required")
    @Pattern(regexp = "NEW|CANCELLED|COMPLETED|PROCESSING",
            message = "Order status must be NEW, CANCELLED, COMPLETED, or PROCESSING")
    private String orderStatus;

    private Long shipVia;
}

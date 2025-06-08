package org.example.bookshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PaymentDTO {
    private Long paymentId;

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Payment date is required")
    private LocalDate paymentDate;

    @NotBlank(message = "Payment status is required")
    @Pattern(regexp = "NEW|PAID|FAILED",
            message = "Payment status must be NEW, PAID, or FAILED")
    private String paymentStatus;
}
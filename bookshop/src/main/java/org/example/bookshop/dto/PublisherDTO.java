package org.example.bookshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PublisherDTO {
    private Long publisherId;

    @NotBlank(message = "Publisher name is required")
    @Size(max = 100, message = "Publisher name cannot exceed 100 characters")
    private String publisherName;

    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country cannot exceed 50 characters")
    private String country;
}

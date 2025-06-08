package org.example.bookshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long customerId;

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name cannot exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name cannot exceed 100 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @NotBlank(message = "Phone is required")
    @Size(max = 20, message = "Phone cannot exceed 20 characters")
    private String phone;

    @NotBlank(message = "Address is required")
    @Size(max = 200, message = "Address cannot exceed 200 characters")
    private String address;

    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City cannot exceed 50 characters")
    private String city;

    @NotBlank(message = "Postal code is required")
    @Size(max = 10, message = "Postal code cannot exceed 10 characters")
    private String postalCode;

    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country cannot exceed 50 characters")
    private String country;
}

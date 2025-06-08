package org.example.bookshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class GameDetailDTO {
    @NotNull(message = "Game ID is required")
    private Long gameId;

    @NotBlank(message = "Platform is required")
    @Size(max = 50, message = "Platform cannot exceed 50 characters")
    private String platform;

    @NotBlank(message = "Developer is required")
    @Size(max = 50, message = "Developer cannot exceed 50 characters")
    private String developer;

    @NotNull(message = "Release year is required")
    @Min(value = 1900, message = "Release year must be at least 1900")
    private Integer releaseYear;

    @NotBlank(message = "Description is required")
    @Size(max = 100, message = "Description cannot exceed 100 characters")
    private String description;
}

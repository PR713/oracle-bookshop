package org.example.bookshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BookDetailDTO {
    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotBlank(message = "ISBN is required")
    @Size(max = 20, message = "ISBN cannot exceed 20 characters")
    private String isbn;

    @NotNull(message = "Publication year is required")
    @Min(value = 1900, message = "Publication year must be at least 1900")
    private Integer publicationYear;

    @NotNull(message = "Publisher ID is required")
    private Long publisherId;

    @NotBlank(message = "Language is required")
    @Size(max = 50, message = "Language cannot exceed 50 characters")
    private String language;

    @NotNull(message = "Page count is required")
    @Min(value = 1, message = "Page count must be at least 1")
    private Integer pageCount;

    @NotBlank(message = "Description is required")
    @Size(max = 100, message = "Description cannot exceed 100 characters")
    private String description;
}

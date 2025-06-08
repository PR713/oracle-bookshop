// BookAuthorDTO.java
package org.example.bookshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BookAuthorDTO {
    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Author ID is required")
    private Long authorId;
}

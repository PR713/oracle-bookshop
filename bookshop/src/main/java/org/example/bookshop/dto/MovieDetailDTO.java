package org.example.bookshop.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MovieDetailDTO {
    private Long movieId; // jeśli endpoint POST to ustawione na null,
    //jeśli PUT to na @PathVariable z URL

    @Valid
    @NotNull(message = "Product information is required")
    private ProductDTO product;

    // specificzne pola dla movie
    @NotBlank(message = "Director is required")
    @Size(max = 100, message = "Director cannot exceed 100 characters")
    private String director;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer durationInMinutes;

    @NotNull(message = "Release year is required")
    @Min(value = 1900, message = "Release year must be at least 1900")
    private Integer releaseYear;

    @NotBlank(message = "Language is required")
    @Size(max = 50, message = "Language cannot exceed 50 characters")
    private String language;

    @NotBlank(message = "Genre is required")
    @Size(max = 50, message = "Genre cannot exceed 50 characters")
    private String genre;

    @NotBlank(message = "Description is required")
    @Size(max = 100, message = "Description cannot exceed 100 characters")
    private String description;

    public MovieDetailDTO() {}

    public MovieDetailDTO(Long movieId, ProductDTO product, String director,
                          Integer durationInMinutes, Integer releaseYear,
                          String language, String genre, String description) {
        this.movieId = movieId;
        this.product = product;
        this.director = director;
        this.durationInMinutes = durationInMinutes;
        this.releaseYear = releaseYear;
        this.language = language;
        this.genre = genre;
        this.description = description;
    }
}
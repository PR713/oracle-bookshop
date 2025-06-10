package org.example.bookshop.controller;

import jakarta.validation.Valid;
import org.example.bookshop.dto.MovieDetailDTO;
import org.example.bookshop.repository.ProductRepository;
import org.example.bookshop.service.MovieDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieDetailController {

    private final MovieDetailService movieDetailService;
    private final ProductRepository productRepository;

    public MovieDetailController(MovieDetailService movieDetailService, ProductRepository productRepository) {
        this.movieDetailService = movieDetailService;
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<List<MovieDetailDTO>> getAllMovies() {
        List<MovieDetailDTO> movies = movieDetailService.findAll();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailDTO> getMovieById(@PathVariable Long id) {
        Optional<MovieDetailDTO> movie = movieDetailService.findById(id);
        return movie.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MovieDetailDTO> createMovie(@Valid @RequestBody MovieDetailDTO movieDetailDTO) {
        try {
            MovieDetailDTO savedMovie = movieDetailService.save(movieDetailDTO);
            return ResponseEntity.ok(savedMovie);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDetailDTO> updateMovie(@PathVariable Long id,
                                                      @Valid @RequestBody MovieDetailDTO movieDetailDTO) {
        try {
            movieDetailDTO.setMovieId(id);
            MovieDetailDTO updatedMovie = movieDetailService.save(movieDetailDTO);
            return ResponseEntity.ok(updatedMovie);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        try {
            movieDetailService.deleteById(id);
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
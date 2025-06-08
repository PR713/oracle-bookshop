package org.example.bookshop.service;

import org.example.bookshop.dto.MovieDetailDTO;
import org.example.bookshop.entity.MovieDetail;
import org.example.bookshop.entity.Product;
import org.example.bookshop.repository.MovieDetailRepository;
import org.example.bookshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieDetailRepository movieDetailRepository;
    private final ProductRepository productRepository;

    public MovieService(MovieDetailRepository movieDetailRepository,
                        ProductRepository productRepository) {
        this.movieDetailRepository = movieDetailRepository;
        this.productRepository = productRepository;
    }

    public List<MovieDetailDTO> findAll() {
        return movieDetailRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<MovieDetailDTO> findById(Long id) {
        return movieDetailRepository.findById(id)
                .map(this::convertToDTO);
    }

    public MovieDetailDTO save(MovieDetailDTO movieDetailDTO) {
        MovieDetail movieDetail = convertToEntity(movieDetailDTO);
        MovieDetail savedMovieDetail = movieDetailRepository.save(movieDetail);
        return convertToDTO(savedMovieDetail);
    }

    public void deleteById(Long id) {
        movieDetailRepository.deleteById(id);
    }

    private MovieDetailDTO convertToDTO(MovieDetail movieDetail) {
        MovieDetailDTO dto = new MovieDetailDTO();
        dto.setMovieId(movieDetail.getMovieID());
        dto.setDirector(movieDetail.getDirector());
        dto.setDurationInMinutes(movieDetail.getDurationInMinutes());
        dto.setReleaseYear(movieDetail.getReleaseYear());
        dto.setLanguage(movieDetail.getLanguage());
        dto.setGenre(movieDetail.getGenre());
        dto.setDescription(movieDetail.getDescription());
        return dto;
    }

    private MovieDetail convertToEntity(MovieDetailDTO dto) {
        MovieDetail movieDetail = new MovieDetail();

        if (dto.getMovieId() != null) {
            Product product = productRepository.findById(dto.getMovieId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + dto.getMovieId()));
            movieDetail.setProduct(product);
        }

        movieDetail.setDirector(dto.getDirector());
        movieDetail.setDurationInMinutes(dto.getDurationInMinutes());
        movieDetail.setReleaseYear(dto.getReleaseYear());
        movieDetail.setLanguage(dto.getLanguage());
        movieDetail.setGenre(dto.getGenre());
        movieDetail.setDescription(dto.getDescription());

        return movieDetail;
    }
}
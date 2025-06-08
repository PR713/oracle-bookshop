package org.example.bookshop.service;

import org.example.bookshop.dto.MovieDetailDTO;
import org.example.bookshop.entity.MovieDetail;
import org.example.bookshop.entity.Product;
import org.example.bookshop.entity.Category;
import org.example.bookshop.repository.MovieDetailRepository;
import org.example.bookshop.repository.ProductRepository;
import org.example.bookshop.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieDetailService {

    private final MovieDetailRepository movieDetailRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public MovieDetailService(MovieDetailRepository movieDetailRepository,
                              ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.movieDetailRepository = movieDetailRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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

    @Transactional
    public MovieDetailDTO save(MovieDetailDTO movieDetailDTO) {

        MovieDetail movieDetail;
        if (movieDetailDTO.getMovieId() != null) {
            movieDetail = movieDetailRepository.findById(movieDetailDTO.getMovieId())
                    .orElse(new MovieDetail());
        } else {
            movieDetail = new MovieDetail();
        }

        movieDetail.setDirector(movieDetailDTO.getDirector());
        movieDetail.setDurationInMinutes(movieDetailDTO.getDurationInMinutes());
        movieDetail.setReleaseYear(movieDetailDTO.getReleaseYear());
        movieDetail.setLanguage(movieDetailDTO.getLanguage());
        movieDetail.setGenre(movieDetailDTO.getGenre());
        movieDetail.setDescription(movieDetailDTO.getDescription());

        MovieDetail savedMovieDetail = movieDetailRepository.save(movieDetail);
        return convertToDTO(savedMovieDetail);
    }

    public void deleteById(Long id) {
        if (!movieDetailRepository.existsById(id)) {
            throw new RuntimeException("Movie not found");
        }
        movieDetailRepository.deleteById(id);
        productRepository.deleteById(id);
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
}
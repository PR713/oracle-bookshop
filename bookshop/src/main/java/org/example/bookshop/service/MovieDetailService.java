package org.example.bookshop.service;

import org.example.bookshop.dto.MovieDetailDTO;
import org.example.bookshop.dto.ProductDTO;
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
        Product product;
        MovieDetail movieDetail;

        if (movieDetailDTO.getMovieId() != null) {
            product = productRepository.findById(movieDetailDTO.getMovieId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + movieDetailDTO.getMovieId()));
            movieDetail = movieDetailRepository.findById(movieDetailDTO.getMovieId())
                    .orElseThrow(() -> new RuntimeException("Movie detail not found: " + movieDetailDTO.getMovieId()));

            ProductDTO productDTO = movieDetailDTO.getProduct();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setStock(productDTO.getStock());
            product.setRating(productDTO.getRating());
        } else {
            Category movieCategory = categoryRepository.findByCategoryName("Movies")
                    .orElseThrow(() -> new RuntimeException("Movies category not found"));

            ProductDTO productDTO = movieDetailDTO.getProduct();
            product = new Product();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setStock(productDTO.getStock());
            product.setRating(productDTO.getRating());
            product.setCategory(movieCategory);

            product = productRepository.save(product);

            movieDetail = new MovieDetail();
            movieDetail.setProduct(product);
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

    @Transactional
    public void deleteById(Long id) {
        if (!movieDetailRepository.existsById(id)) {
            throw new RuntimeException("Movie not found");
        }
        movieDetailRepository.deleteById(id);
        productRepository.deleteById(id);
    }

    private MovieDetailDTO convertToDTO(MovieDetail movieDetail) {
        Product product = movieDetail.getProduct();
        ProductDTO productDTO = new ProductDTO(
                product.getProductID(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getRating(),
                product.getCategory().getCategoryID()
        );

        MovieDetailDTO dto = new MovieDetailDTO(
                product.getProductID(),
                productDTO,
                movieDetail.getDirector(),
                movieDetail.getDurationInMinutes(),
                movieDetail.getReleaseYear(),
                movieDetail.getLanguage(),
                movieDetail.getGenre(),
                movieDetail.getDescription()
        );
        return dto;
    }
}
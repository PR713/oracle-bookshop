package org.example.bookshop.service;

import org.example.bookshop.dto.ProductDTO;
import org.example.bookshop.entity.*;
import org.example.bookshop.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BookDetailRepository bookDetailRepository;
    private final MovieDetailRepository movieDetailRepository;
    private final GameDetailRepository gameDetailRepository;
    private final AccessoriesDetailRepository accessoriesDetailRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          BookDetailRepository bookDetailRepository,
                          MovieDetailRepository movieDetailRepository,
                          GameDetailRepository gameDetailRepository,
                          AccessoriesDetailRepository accessoriesDetailRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.bookDetailRepository = bookDetailRepository;
        this.movieDetailRepository = movieDetailRepository;
        this.gameDetailRepository = gameDetailRepository;
        this.accessoriesDetailRepository = accessoriesDetailRepository;
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> findById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ProductDTO save(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    @Transactional
    public Product save(Product product) {
        // Sprawdź i ustaw kategorię
        if (product.getCategory() != null && product.getCategory().getCategoryID() != null) {
            Category category = categoryRepository.findById(product.getCategory().getCategoryID())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }


        Product savedProduct = productRepository.save(product);


        String categoryName = savedProduct.getCategory().getCategoryName();

        if ("Book".equals(categoryName)) {
            //todo controllers :)
        } else if ("Movie".equals(categoryName)) {
            saveMovieDetails(savedProduct);
        } else if ("Game".equals(categoryName)) {
            //todo controllers :)
        } else if ("Accessories".equals(categoryName)) {
            //todo controllers :)
        }

        return savedProduct;
    }


    private void saveMovieDetails(Product product) {
        Optional<MovieDetail> existingMovieDetail = movieDetailRepository.findById(product.getProductID());

        MovieDetail movieDetail;
        if (existingMovieDetail.isPresent()) {
            movieDetail = existingMovieDetail.get();
        } else {
            movieDetail = new MovieDetail();
            movieDetail.setProduct(product);

            movieDetail.setDirector("Unknown Director");
            movieDetail.setDurationInMinutes(120);
            movieDetail.setReleaseYear(2024);
            movieDetail.setLanguage("English");
            movieDetail.setGenre("Drama");
            movieDetail.setDescription("No description available");
        }

        movieDetailRepository.save(movieDetail);
    }

    private void saveGameDetails(Product product) {
        Optional<GameDetail> existingGameDetail = gameDetailRepository.findById(product.getProductID());

        GameDetail gameDetail;
        if (existingGameDetail.isPresent()) {
            gameDetail = existingGameDetail.get();
        } else {
            gameDetail = new GameDetail();
            gameDetail.setProduct(product);
            // Ustaw domyślne wartości
            gameDetail.setPlatform("PC");
            gameDetail.setDeveloper("Unknown Developer");
            gameDetail.setReleaseYear(2024);
            gameDetail.setDescription("No description available");
        }

        gameDetailRepository.save(gameDetail);
    }

    private void saveAccessoriesDetails(Product product) {
        Optional<AccessoriesDetail> existingAccessoriesDetail = accessoriesDetailRepository.findById(product.getProductID());

        AccessoriesDetail accessoriesDetail;
        if (existingAccessoriesDetail.isPresent()) {
            accessoriesDetail = existingAccessoriesDetail.get();
        } else {
            accessoriesDetail = new AccessoriesDetail();
            accessoriesDetail.setProduct(product);
            // Ustaw domyślne wartości
            accessoriesDetail.setDescription("No description available");
        }

        accessoriesDetailRepository.save(accessoriesDetail);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductID());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setRating(product.getRating());
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getCategoryID());
        }
        return dto;
    }

    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setRating(dto.getRating());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found: " + dto.getCategoryId()));
            product.setCategory(category);
        }

        return product;
    }
}
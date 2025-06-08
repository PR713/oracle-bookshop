package org.example.bookshop.service;

import org.example.bookshop.dto.GameDetailDTO;
import org.example.bookshop.entity.GameDetail;
import org.example.bookshop.entity.Product;
import org.example.bookshop.repository.GameDetailRepository;
import org.example.bookshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {
    private final GameDetailRepository gameDetailRepository;
    private final ProductRepository productRepository;

    public GameService(GameDetailRepository gameDetailRepository,
                       ProductRepository productRepository) {
        this.gameDetailRepository = gameDetailRepository;
        this.productRepository = productRepository;
    }

    public List<GameDetailDTO> findAll() {
        return gameDetailRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<GameDetailDTO> findById(Long id) {
        return gameDetailRepository.findById(id)
                .map(this::convertToDTO);
    }

    public GameDetailDTO save(GameDetailDTO gameDetailDTO) {
        GameDetail gameDetail = convertToEntity(gameDetailDTO);
        GameDetail savedGameDetail = gameDetailRepository.save(gameDetail);
        return convertToDTO(savedGameDetail);
    }

    public void deleteById(Long id) {
        gameDetailRepository.deleteById(id);
    }

    private GameDetailDTO convertToDTO(GameDetail gameDetail) {
        GameDetailDTO dto = new GameDetailDTO();
        dto.setGameId(gameDetail.getGameID());
        dto.setPlatform(gameDetail.getPlatform());
        dto.setDeveloper(gameDetail.getDeveloper());
        dto.setReleaseYear(gameDetail.getReleaseYear());
        dto.setDescription(gameDetail.getDescription());
        return dto;
    }

    private GameDetail convertToEntity(GameDetailDTO dto) {
        GameDetail gameDetail = new GameDetail();

        if (dto.getGameId() != null) {
            Product product = productRepository.findById(dto.getGameId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + dto.getGameId()));
            gameDetail.setProduct(product);
        }

        gameDetail.setPlatform(dto.getPlatform());
        gameDetail.setDeveloper(dto.getDeveloper());
        gameDetail.setReleaseYear(dto.getReleaseYear());
        gameDetail.setDescription(dto.getDescription());

        return gameDetail;
    }
}
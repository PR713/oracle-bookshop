package org.example.bookshop.service;

import org.example.bookshop.dto.AccessoriesDetailDTO;
import org.example.bookshop.entity.AccessoriesDetail;
import org.example.bookshop.entity.Product;
import org.example.bookshop.repository.AccessoriesDetailRepository;
import org.example.bookshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccessoriesService {
    private final AccessoriesDetailRepository accessoryDetailRepository;
    private final ProductRepository productRepository;

    public AccessoriesService(AccessoriesDetailRepository accessoryDetailRepository,
                              ProductRepository productRepository) {
        this.accessoryDetailRepository = accessoryDetailRepository;
        this.productRepository = productRepository;
    }

    public List<AccessoriesDetailDTO> findAll() {
        return accessoryDetailRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<AccessoriesDetailDTO> findById(Long id) {
        return accessoryDetailRepository.findById(id)
                .map(this::convertToDTO);
    }

    public AccessoriesDetailDTO save(AccessoriesDetailDTO accessoryDetailDTO) {
        AccessoriesDetail accessoryDetail = convertToEntity(accessoryDetailDTO);
        AccessoriesDetail savedAccessoriesDetail = accessoryDetailRepository.save(accessoryDetail);
        return convertToDTO(savedAccessoriesDetail);
    }

    public void deleteById(Long id) {
        accessoryDetailRepository.deleteById(id);
    }

    private AccessoriesDetailDTO convertToDTO(AccessoriesDetail accessoryDetail) {
        AccessoriesDetailDTO dto = new AccessoriesDetailDTO();
        dto.setAccessoryId(accessoryDetail.getAccessoryID());
        dto.setDescription(accessoryDetail.getDescription());
        return dto;
    }

    private AccessoriesDetail convertToEntity(AccessoriesDetailDTO dto) {
        AccessoriesDetail accessoryDetail = new AccessoriesDetail();

        if (dto.getAccessoryId() != null) {
            Product product = productRepository.findById(dto.getAccessoryId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + dto.getAccessoryId()));
            accessoryDetail.setProduct(product);
        }

        accessoryDetail.setDescription(dto.getDescription());

        return accessoryDetail;
    }
}
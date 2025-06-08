package org.example.bookshop.service;

import org.example.bookshop.dto.BookDetailDTO;
import org.example.bookshop.entity.BookDetail;
import org.example.bookshop.entity.Product;
import org.example.bookshop.entity.Publisher;
import org.example.bookshop.repository.BookDetailRepository;
import org.example.bookshop.repository.ProductRepository;
import org.example.bookshop.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookDetailRepository bookDetailRepository;
    private final ProductRepository productRepository;
    private final PublisherRepository publisherRepository;

    public BookService(BookDetailRepository bookDetailRepository,
                       ProductRepository productRepository,
                       PublisherRepository publisherRepository) {
        this.bookDetailRepository = bookDetailRepository;
        this.productRepository = productRepository;
        this.publisherRepository = publisherRepository;
    }

    public List<BookDetailDTO> findAll() {
        return bookDetailRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<BookDetailDTO> findById(Long id) {
        return bookDetailRepository.findById(id)
                .map(this::convertToDTO);
    }

    public BookDetailDTO save(BookDetailDTO bookDetailDTO) {
        BookDetail bookDetail = convertToEntity(bookDetailDTO);
        BookDetail savedBookDetail = bookDetailRepository.save(bookDetail);
        return convertToDTO(savedBookDetail);
    }

    public void deleteById(Long id) {
        bookDetailRepository.deleteById(id);
    }

    private BookDetailDTO convertToDTO(BookDetail bookDetail) {
        BookDetailDTO dto = new BookDetailDTO();
        dto.setBookId(bookDetail.getBookID());
        dto.setIsbn(bookDetail.getIsbn());
        dto.setPublicationYear(bookDetail.getPublicationYear());
        if (bookDetail.getPublisher() != null) {
            dto.setPublisherId(bookDetail.getPublisher().getPublisherID());
        }
        dto.setLanguage(bookDetail.getLanguage());
        dto.setPageCount(bookDetail.getPageCount());
        dto.setDescription(bookDetail.getDescription());
        return dto;
    }

    private BookDetail convertToEntity(BookDetailDTO dto) {
        BookDetail bookDetail = new BookDetail();

        if (dto.getBookId() != null) {
            Product product = productRepository.findById(dto.getBookId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + dto.getBookId()));
            bookDetail.setProduct(product);
        }

        bookDetail.setIsbn(dto.getIsbn());
        bookDetail.setPublicationYear(dto.getPublicationYear());

        if (dto.getPublisherId() != null) {
            Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                    .orElseThrow(() -> new RuntimeException("Publisher not found: " + dto.getPublisherId()));
            bookDetail.setPublisher(publisher);
        }

        bookDetail.setLanguage(dto.getLanguage());
        bookDetail.setPageCount(dto.getPageCount());
        bookDetail.setDescription(dto.getDescription());

        return bookDetail;
    }
}
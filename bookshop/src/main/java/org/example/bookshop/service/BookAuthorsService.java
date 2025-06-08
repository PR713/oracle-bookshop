package org.example.bookshop.service;

import org.example.bookshop.dto.BookAuthorDTO;
import org.example.bookshop.entity.BookAuthor;
import org.example.bookshop.entity.BookAuthorId;
import org.example.bookshop.entity.BookDetail;
import org.example.bookshop.entity.Author;
import org.example.bookshop.repository.BookAuthorRepository;
import org.example.bookshop.repository.BookDetailRepository;
import org.example.bookshop.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookAuthorsService {
    private final BookAuthorRepository bookAuthorRepository;
    private final BookDetailRepository bookDetailRepository;
    private final AuthorRepository authorRepository;

    public BookAuthorsService(BookAuthorRepository bookAuthorRepository,
                              BookDetailRepository bookDetailRepository,
                              AuthorRepository authorRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookDetailRepository = bookDetailRepository;
        this.authorRepository = authorRepository;
    }

    public List<BookAuthorDTO> findAll() {
        return bookAuthorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BookAuthorDTO> findByBookId(Long bookId) {
        return bookAuthorRepository.findAll().stream()
                .filter(ba -> ba.getBook().getBookID().equals(bookId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BookAuthorDTO> findByAuthorId(Long authorId) {
        return bookAuthorRepository.findAll().stream()
                .filter(ba -> ba.getAuthor().getAuthorID().equals(authorId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<BookAuthorDTO> findById(Long bookId, Long authorId) {
        BookAuthorId id = new BookAuthorId(bookId, authorId);
        return bookAuthorRepository.findById(id)
                .map(this::convertToDTO);
    }

    public BookAuthorDTO save(BookAuthorDTO bookAuthorDTO) {
        BookAuthor bookAuthor = convertToEntity(bookAuthorDTO);
        BookAuthor savedBookAuthor = bookAuthorRepository.save(bookAuthor);
        return convertToDTO(savedBookAuthor);
    }

    public void deleteById(Long bookId, Long authorId) {
        BookAuthorId id = new BookAuthorId(bookId, authorId);
        bookAuthorRepository.deleteById(id);
    }

    public void deleteByBookId(Long bookId) {
        List<BookAuthor> toDelete = bookAuthorRepository.findAll().stream()
                .filter(ba -> ba.getBook().getBookID().equals(bookId))
                .collect(Collectors.toList());
        bookAuthorRepository.deleteAll(toDelete);
    }

    public void deleteByAuthorId(Long authorId) {
        List<BookAuthor> toDelete = bookAuthorRepository.findAll().stream()
                .filter(ba -> ba.getAuthor().getAuthorID().equals(authorId))
                .collect(Collectors.toList());
        bookAuthorRepository.deleteAll(toDelete);
    }

    private BookAuthorDTO convertToDTO(BookAuthor bookAuthor) {
        BookAuthorDTO dto = new BookAuthorDTO();
        dto.setBookId(bookAuthor.getBook().getBookID());
        dto.setAuthorId(bookAuthor.getAuthor().getAuthorID());
        return dto;
    }

    private BookAuthor convertToEntity(BookAuthorDTO dto) {
        BookAuthor bookAuthor = new BookAuthor();

        BookDetail bookDetail = bookDetailRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found: " + dto.getBookId()));
        bookAuthor.setBook(bookDetail);

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found: " + dto.getAuthorId()));
        bookAuthor.setAuthor(author);

        // Ustawienie composite key
        BookAuthorId id = new BookAuthorId(dto.getBookId(), dto.getAuthorId());
        bookAuthor.setId(id);

        return bookAuthor;
    }
}
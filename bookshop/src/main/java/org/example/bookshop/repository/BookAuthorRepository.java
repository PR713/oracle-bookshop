package org.example.bookshop.repository;

import org.example.bookshop.entity.BookAuthor;
import org.example.bookshop.entity.BookAuthorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
}

package org.example.bookshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "BOOK_AUTHORS")
public class BookAuthor {
    @EmbeddedId
    private BookAuthorId id;

    @ManyToOne
    @MapsId("bookID")
    @JoinColumn(name = "BOOK_ID")
    private BookDetail book;

    @ManyToOne
    @MapsId("authorID")
    @JoinColumn(name = "AUTHOR_ID")
    private Author author;
}


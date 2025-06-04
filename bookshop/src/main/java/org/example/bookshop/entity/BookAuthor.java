package org.example.bookshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "BookAuthors")
public class BookAuthor {
    @EmbeddedId
    private BookAuthorId id;

    @ManyToOne
    @MapsId("bookID")
    @JoinColumn(name = "BookID")
    private BookDetail book;

    @ManyToOne
    @MapsId("authorID")
    @JoinColumn(name = "AuthorID")
    private Author author;
}


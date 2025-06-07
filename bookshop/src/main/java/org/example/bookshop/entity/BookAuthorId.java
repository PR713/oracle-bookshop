package org.example.bookshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BookAuthorId implements Serializable {
    @Column(name = "BOOK_ID")
    private Long bookID;

    @Column(name = "AUTHOR_ID")
    private Long authorID;
}

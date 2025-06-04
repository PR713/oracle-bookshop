package org.example.bookshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BookAuthorId implements Serializable {
    @Column(name = "BookID")
    private Long bookID;

    @Column(name = "AuthorID")
    private Long authorID;
}

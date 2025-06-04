package org.example.bookshop.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Authors")
public class Author {
    @Id
    @Column(name = "AuthorID")
    private Long authorID;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Biography")
    private String biography;

    @ManyToMany(mappedBy = "authors")
    private List<BookDetail> books;
}
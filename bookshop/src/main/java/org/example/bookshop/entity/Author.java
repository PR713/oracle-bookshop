package org.example.bookshop.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "AUTHORS")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    @SequenceGenerator(name = "author_seq", sequenceName = "AUTHOR_SEQ", allocationSize = 1)
    @Column(name = "AUTHOR_ID")
    private Long authorID;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "BIOGRAPHY")
    private String biography;

    @ManyToMany(mappedBy = "authors")
    private List<BookDetail> books;
}
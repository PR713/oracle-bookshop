package org.example.bookshop.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "BOOK_DETAILS")
public class BookDetail {
    @Id
    @Column(name = "BOOK_ID")
    private Long bookID;

    @OneToOne
    @MapsId
    @JoinColumn(name = "BOOK_ID")
    private Product product;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "PUBLICATION_YEAR")
    private Integer publicationYear;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "PAGE_COUNT")
    private Integer pageCount;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "PUBLISHER_ID")
    private Publisher publisher;

    @ManyToMany
    @JoinTable(name = "BOOK_AUTHORS", joinColumns = @JoinColumn(name = "BOOK_ID"), inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
    private List<Author> authors;
}

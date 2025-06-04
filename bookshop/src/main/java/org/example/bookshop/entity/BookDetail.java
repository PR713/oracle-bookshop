package org.example.bookshop.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "BookDetails")
public class BookDetail {
    @Id
    @Column(name = "BookID")
    private Long bookID;

    @OneToOne
    @MapsId
    @JoinColumn(name = "BookID")
    private Product product;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "PublicationYear")
    private Integer publicationYear;

    @Column(name = "Language")
    private String language;

    @Column(name = "PageCount")
    private Integer pageCount;

    @Column(name = "Description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "PublisherID")
    private Publisher publisher;

    @ManyToMany
    @JoinTable(name = "BookAuthors", joinColumns = @JoinColumn(name = "BookID"), inverseJoinColumns = @JoinColumn(name = "AuthorID"))
    private List<Author> authors;
}

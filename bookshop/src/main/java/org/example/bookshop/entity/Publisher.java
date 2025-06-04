package org.example.bookshop.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Publishers")
public class Publisher {
    @Id
    @Column(name = "PublisherID")
    private Long publisherID;

    @Column(name = "PublisherName")
    private String publisherName;

    @Column(name = "Country")
    private String country;

    @OneToMany(mappedBy = "publisher")
    private List<BookDetail> books;
}
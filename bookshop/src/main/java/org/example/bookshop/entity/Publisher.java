package org.example.bookshop.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "PUBLISHERS")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publisher_seq")
    @SequenceGenerator(name = "publisher_seq", sequenceName = "PUBLISHER_SEQ", allocationSize = 1)
    @Column(name = "PUBLISHER_ID")
    private Long publisherID;

    @Column(name = "PUBLISHER_NAME")
    private String publisherName;

    @Column(name = "COUNTRY")
    private String country;

    @OneToMany(mappedBy = "publisher")
    private List<BookDetail> books;
}
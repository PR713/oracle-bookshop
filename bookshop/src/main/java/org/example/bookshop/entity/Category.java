package org.example.bookshop.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "Categories")
public class Category {
    @Id
    @Column(name = "CategoryID")
    private Long categoryID;

    @Column(name = "CategoryName")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}

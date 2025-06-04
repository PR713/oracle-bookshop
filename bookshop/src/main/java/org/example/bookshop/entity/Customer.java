package org.example.bookshop.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @Column(name = "CustomerID")
    private Long customerID;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Email")
    private String email;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Address")
    private String address;

    @Column(name = "City")
    private String city;

    @Column(name = "PostalCode")
    private String postalCode;

    @Column(name = "Country")
    private String country;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
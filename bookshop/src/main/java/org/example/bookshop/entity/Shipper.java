package org.example.bookshop.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Shippers")
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    @Column(name = "ShipperID")
    private Long shipperID;

    @Column(name = "CompanyName")
    private String companyName;

    @Column(name = "Phone")
    private String phone;

    @OneToMany(mappedBy = "shipVia")
    private List<Order> orders;
}

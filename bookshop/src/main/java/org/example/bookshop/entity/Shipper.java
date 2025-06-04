package org.example.bookshop.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Shippers")
public class Shipper {
    @Id
    @Column(name = "ShipperID")
    private Long shipperID;

    @Column(name = "CompanyName")
    private String companyName;

    @Column(name = "Phone")
    private String phone;

    @OneToMany(mappedBy = "shipVia")
    private List<Order> orders;
}

package org.example.bookshop.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "SHIPPERS")
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipper_seq")
    @SequenceGenerator(name = "shipper_seq", sequenceName = "SHIPPER_SEQ", allocationSize = 1)
    @Column(name = "SHIPPER_ID")
    private Long shipperID;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "PHONE")
    private String phone;

    @OneToMany(mappedBy = "shipVia")
    private List<Order> orders;
}

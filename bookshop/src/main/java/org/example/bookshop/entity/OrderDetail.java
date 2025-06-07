package org.example.bookshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "OrderDetails")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId id;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "UnitPrice")
    private Float unitPrice;

    @Column(name = "Discount")
    private Float discount;

    @ManyToOne
    @MapsId("orderID")
    @JoinColumn(name = "OrderID")
    private Order order;

    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "ProductID")
    private Product product;


    public OrderDetailId getId() {
        return id;
    }

    public void setId(OrderDetailId id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

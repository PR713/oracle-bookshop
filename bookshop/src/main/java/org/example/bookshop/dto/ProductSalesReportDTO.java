package org.example.bookshop.dto;

import java.math.BigDecimal;

public class ProductSalesReportDTO {
    private Long productId;
    private String productName;
    private Double price;
    private Long totalQuantitySold;
    private BigDecimal totalRevenue;
    private Long totalOrders;

    public ProductSalesReportDTO(Long productId, String productName, Double price,
                                 Long totalQuantitySold, BigDecimal totalRevenue, Long totalOrders) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.totalQuantitySold = totalQuantitySold;
        this.totalRevenue = totalRevenue;
        this.totalOrders = totalOrders;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Long getTotalQuantitySold() { return totalQuantitySold; }
    public void setTotalQuantitySold(Long totalQuantitySold) { this.totalQuantitySold = totalQuantitySold; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public Long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(Long totalOrders) { this.totalOrders = totalOrders; }
}

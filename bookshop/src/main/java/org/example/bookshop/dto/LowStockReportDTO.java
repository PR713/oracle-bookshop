package org.example.bookshop.dto;

import java.math.BigDecimal;

public class LowStockReportDTO {
    private Long productId;
    private String productName;
    private Integer stockQuantity;
    private Float price;
    private String categoryName;
    private Long recentSales;

    public LowStockReportDTO(Long productId, String productName, Integer stockQuantity,
                             Float price, String categoryName, Long recentSales) {
        this.productId = productId;
        this.productName = productName;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.categoryName = categoryName;
        this.recentSales = recentSales;
    }


    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public Float getPrice() { return price; }
    public void setPrice(Float price) { this.price = price; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Long getRecentSales() { return recentSales; }
    public void setRecentSales(Long recentSales) { this.recentSales = recentSales; }
}
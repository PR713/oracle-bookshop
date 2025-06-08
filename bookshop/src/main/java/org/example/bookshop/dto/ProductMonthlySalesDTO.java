package org.example.bookshop.dto;

import java.math.BigDecimal;

public class ProductMonthlySalesDTO {
    private Long productId;
    private String productName;
    private Long quantitySold;
    private BigDecimal revenue;

    public ProductMonthlySalesDTO(Long productId, String productName, Long quantitySold, BigDecimal revenue) {
        this.productId = productId;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.revenue = revenue;
    }


    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Long getQuantitySold() { return quantitySold; }
    public void setQuantitySold(Long quantitySold) { this.quantitySold = quantitySold; }

    public BigDecimal getRevenue() { return revenue; }
    public void setRevenue(BigDecimal revenue) { this.revenue = revenue; }
}

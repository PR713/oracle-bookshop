package org.example.bookshop.dto;

import java.math.BigDecimal;
import java.util.List;

public class MonthlySalesReportDTO {
    private int year;
    private int month;
    private Long totalOrders;
    private BigDecimal totalRevenue;
    private Long uniqueCustomers;
    private BigDecimal averageOrderValue;
    private List<ProductMonthlySalesDTO> productSales;

    public MonthlySalesReportDTO(int year, int month, Long totalOrders, BigDecimal totalRevenue,
                                 Long uniqueCustomers, BigDecimal averageOrderValue,
                                 List<ProductMonthlySalesDTO> productSales) {
        this.year = year;
        this.month = month;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.uniqueCustomers = uniqueCustomers;
        this.averageOrderValue = averageOrderValue;
        this.productSales = productSales;
    }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }

    public Long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(Long totalOrders) { this.totalOrders = totalOrders; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public Long getUniqueCustomers() { return uniqueCustomers; }
    public void setUniqueCustomers(Long uniqueCustomers) { this.uniqueCustomers = uniqueCustomers; }

    public BigDecimal getAverageOrderValue() { return averageOrderValue; }
    public void setAverageOrderValue(BigDecimal averageOrderValue) { this.averageOrderValue = averageOrderValue; }

    public List<ProductMonthlySalesDTO> getProductSales() { return productSales; }
    public void setProductSales(List<ProductMonthlySalesDTO> productSales) { this.productSales = productSales; }
}
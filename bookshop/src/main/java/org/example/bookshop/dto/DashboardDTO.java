package org.example.bookshop.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardDTO {
    private Long todayOrders;
    private BigDecimal todayRevenue;
    private List<LowStockReportDTO> lowStockProducts;
    private List<RecentOrderDTO> recentOrders;

    public DashboardDTO(Long todayOrders, BigDecimal todayRevenue,
                        List<LowStockReportDTO> lowStockProducts, List<RecentOrderDTO> recentOrders) {
        this.todayOrders = todayOrders;
        this.todayRevenue = todayRevenue;
        this.lowStockProducts = lowStockProducts;
        this.recentOrders = recentOrders;
    }

    public Long getTodayOrders() { return todayOrders; }
    public void setTodayOrders(Long todayOrders) { this.todayOrders = todayOrders; }

    public BigDecimal getTodayRevenue() { return todayRevenue; }
    public void setTodayRevenue(BigDecimal todayRevenue) { this.todayRevenue = todayRevenue; }

    public List<LowStockReportDTO> getLowStockProducts() { return lowStockProducts; }
    public void setLowStockProducts(List<LowStockReportDTO> lowStockProducts) { this.lowStockProducts = lowStockProducts; }

    public List<RecentOrderDTO> getRecentOrders() { return recentOrders; }
    public void setRecentOrders(List<RecentOrderDTO> recentOrders) { this.recentOrders = recentOrders; }
}
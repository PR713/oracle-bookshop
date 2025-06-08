package org.example.bookshop.service;

import org.example.bookshop.dto.*;
import org.example.bookshop.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReportingService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final PaymentRepository paymentRepository;

    public ReportingService(ProductRepository productRepository,
                            OrderRepository orderRepository,
                            CustomerRepository customerRepository,
                            PaymentRepository paymentRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Top 10 najlepiej sprzedających się produktów
     */
    public List<ProductSalesReportDTO> getTopSellingProducts() {
        String jpql = """
            SELECT new org.example.bookshop.dto.ProductSalesReportDTO(
                p.productID,
                p.name,
                p.price,
                SUM(od.quantity),
                SUM(od.quantity * od.unitPrice),
                COUNT(DISTINCT o.orderID)
            )
            FROM OrderDetail od
            JOIN od.product p
            JOIN od.order o
            WHERE o.orderStatus = 'COMPLETED'
            GROUP BY p.productId, p.name, p.price
            ORDER BY SUM(od.quantity) DESC
            """;

        return entityManager.createQuery(jpql, ProductSalesReportDTO.class)
                .setMaxResults(10)
                .getResultList();
    }

    /**
     * Statystyki sprzedaży za bieżący miesiąc
     */
    public MonthlySalesReportDTO getCurrentMonthSales() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        String mainStatsJpql = """
            SELECT
                COUNT(DISTINCT o.orderID) as totalOrders,
                COALESCE(SUM(p.amount), 0) as totalRevenue,
                COUNT(DISTINCT o.customer.customerID) as uniqueCustomers,
                COALESCE(AVG(p.amount), 0) as averageOrderValue
            FROM Order o
            JOIN Payment p ON p.order.orderID = o.orderID
            WHERE YEAR(o.orderDate) = :year
            AND MONTH(o.orderDate) = :month
            AND o.orderStatus = 'COMPLETED'
            """;

        Object[] mainStats = (Object[]) entityManager.createQuery(mainStatsJpql)
                .setParameter("year", year)
                .setParameter("month", month)
                .getSingleResult();

        String productSalesJpql = """
            SELECT new org.example.bookshop.dto.ProductMonthlySalesDTO(
                p.productID,
                p.name,
                SUM(od.quantity),
                SUM(od.quantity * od.unitPrice)
            )
            FROM OrderDetail od
            JOIN od.product p
            JOIN od.order o
            WHERE YEAR(o.orderDate) = :year
            AND MONTH(o.orderDate) = :month
            AND o.orderStatus = 'COMPLETED'
            GROUP BY p.productID, p.name
            ORDER BY SUM(od.quantity * od.unitPrice) DESC
            """;

        List<ProductMonthlySalesDTO> productSales = entityManager
                .createQuery(productSalesJpql, ProductMonthlySalesDTO.class)
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();

        return new MonthlySalesReportDTO(
                year,
                month,
                ((Number) mainStats[0]).longValue(),
                (BigDecimal) mainStats[1],
                ((Number) mainStats[2]).longValue(),
                (BigDecimal) mainStats[3],
                productSales
        );
    }

    /**
     * Produkty z niskim stanem magazynowym (poniżej 10 sztuk)
     */
    public List<LowStockReportDTO> getLowStockProducts() {
        String jpql = """
            SELECT new org.example.bookshop.dto.LowStockReportDTO(
                p.productID,
                p.name,
                p.stock,
                p.price,
                cat.categoryName,
                COALESCE(SUM(od.quantity), 0)
            )
            FROM Product p
            LEFT JOIN Category cat ON p.category.categoryID = cat.categoryID
            LEFT JOIN OrderDetail od ON p.productID = od.product.productID
            LEFT JOIN Order o ON od.order.orderID = o.orderID
                AND o.orderDate >= :lastMonth
                AND o.orderStatus = 'COMPLETED'
            WHERE p.stock <= 10
            GROUP BY p.productID, p.name, p.stock, p.price, cat.categoryName
            ORDER BY p.stock ASC
            """;

        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);

        return entityManager.createQuery(jpql, LowStockReportDTO.class)
                .setParameter("lastMonth", lastMonth)
                .getResultList();
    }

    /**
     * Dashboard z kluczowymi metrykami
     */
    public DashboardDTO getDashboardStats() {
        // Dzisiejsze statystyki
        String todayStatsJpql = """
            SELECT
                COUNT(o.orderID),
                COALESCE(SUM(p.amount), 0)
            FROM Order o
            LEFT JOIN Payment p ON o.orderID = p.order.orderID
            WHERE DATE(o.orderDate) = CURRENT_DATE
            AND o.orderStatus = 'COMPLETED'
            """;

        Object[] todayStats = (Object[]) entityManager.createQuery(todayStatsJpql)
                .getSingleResult();

        // Ostatnie zamówienia
        String recentOrdersJpql = """
            SELECT new org.example.bookshop.dto.RecentOrderDTO(
                o.orderID,
                c.customerName,
                o.orderDate,
                p.amount,
                o.orderStatus
            )
            FROM Order o
            JOIN Customer c ON o.customer.customerID = c.customerID
            LEFT JOIN Payment p ON o.orderID = p.order.orderID
            ORDER BY o.orderDate DESC
            """;

        List<RecentOrderDTO> recentOrders = entityManager
                .createQuery(recentOrdersJpql, RecentOrderDTO.class)
                .setMaxResults(5)
                .getResultList();

        return new DashboardDTO(
                ((Number) todayStats[0]).longValue(),
                (BigDecimal) todayStats[1],
                getLowStockProducts(),
                recentOrders
        );
    }
}
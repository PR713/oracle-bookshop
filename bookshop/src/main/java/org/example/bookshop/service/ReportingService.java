package org.example.bookshop.service;

import org.example.bookshop.dto.*;
import org.example.bookshop.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Service
@Transactional(readOnly = true)
public class ReportingService {

    private static final Logger log = LoggerFactory.getLogger(ReportingService.class);

    @PersistenceContext
    private EntityManager entityManager;

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public ReportingService(ProductRepository productRepository,
                            OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Produkty z niskim stanem magazynowym (poniżej 10 sztuk)
     */
    public List<LowStockReportDTO> getLowStockProducts() {
        log.info("Generating low stock products report");

        try {
            String productJpql = """
                SELECT p.productId, p.name, p.stock, p.price, c.categoryName
                FROM Product p
                LEFT JOIN p.category c
                WHERE p.stock <= 10
                ORDER BY p.stock ASC
                """;

            List<Object[]> productResults = entityManager.createQuery(productJpql, Object[].class)
                    .getResultList();

            List<LowStockReportDTO> reports = new ArrayList<>();
            LocalDate lastMonth = LocalDate.now().minusMonths(1);

            for (Object[] row : productResults) {
                Long productId = (Long) row[0];
                String productName = (String) row[1];
                Integer stockQuantity = (Integer) row[2];
                Float price = (Float) row[3];
                String categoryName = row[4] != null ? (String) row[4] : "Bez kategorii";

                String salesJpql = """
                    SELECT COALESCE(SUM(od.quantity), 0)
                    FROM OrderDetail od
                    JOIN od.order o
                    WHERE od.product.productId = :productId
                    AND o.orderDate >= :lastMonth
                    AND o.orderStatus = 'COMPLETED'
                    """;

                Long recentSales = entityManager.createQuery(salesJpql, Long.class)
                        .setParameter("productId", productId)
                        .setParameter("lastMonth", lastMonth)
                        .getSingleResult();

                reports.add(new LowStockReportDTO(
                        productId, productName, stockQuantity, price, categoryName, recentSales
                ));
            }

            log.info("Low stock report generated successfully with {} products", reports.size());
            return reports;

        } catch (Exception e) {
            log.error("Error generating low stock products report", e);
            throw new RuntimeException("Failed to generate low stock report", e);
        }
    }

    /**
     * Miesięczny raport zamówień za bieżący miesiąc
     */
    public MonthlyOrdersReportDTO getCurrentMonthOrdersReport() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        log.info("Generating monthly orders report for {}/{}", month, year);

        try {
            String ordersJpql = """
                SELECT o.orderID, 
                       CONCAT(c.firstName, ' ', c.lastName), 
                       o.orderDate, 
                       o.orderStatus
                FROM Order o
                JOIN o.customer c
                WHERE EXTRACT(YEAR FROM o.orderDate) = :year
                AND EXTRACT(MONTH FROM o.orderDate) = :month
                ORDER BY o.orderDate DESC
                """;

            List<Object[]> orderResults = entityManager.createQuery(ordersJpql, Object[].class)
                    .setParameter("year", year)
                    .setParameter("month", month)
                    .getResultList();

            List<MonthlyOrderDTO> orders = new ArrayList<>();
            BigDecimal totalRevenue = BigDecimal.ZERO;
            Long completedOrders = 0L;

            for (Object[] row : orderResults) {
                Long orderId = (Long) row[0];
                String customerName = (String) row[1];
                LocalDate orderDate = (LocalDate) row[2];
                String orderStatus = (String) row[3];

                String orderValueJpql = """
                    SELECT COALESCE(SUM(od.quantity * od.unitPrice), 0)
                    FROM OrderDetail od
                    WHERE od.order.orderID = :orderId
                    """;

                Double orderValueDouble = entityManager.createQuery(orderValueJpql, Double.class)
                        .setParameter("orderId", orderId)
                        .getSingleResult();

                BigDecimal orderValue = orderValueDouble != null ?
                        BigDecimal.valueOf(orderValueDouble) : BigDecimal.ZERO;

                orders.add(new MonthlyOrderDTO(orderId, customerName, orderDate, orderValue, orderStatus));

                if ("COMPLETED".equals(orderStatus)) {
                    totalRevenue = totalRevenue.add(orderValue);
                    completedOrders++;
                }
            }

            String uniqueCustomersJpql = """
                SELECT COUNT(DISTINCT o.customer.customerID)
                FROM Order o
                WHERE EXTRACT(YEAR FROM o.orderDate) = :year
                AND EXTRACT(MONTH FROM o.orderDate) = :month
                AND o.orderStatus = 'COMPLETED'
                """;

            Long uniqueCustomers = entityManager.createQuery(uniqueCustomersJpql, Long.class)
                    .setParameter("year", year)
                    .setParameter("month", month)
                    .getSingleResult();

            BigDecimal averageOrderValue = BigDecimal.ZERO;
            if (completedOrders > 0) {
                averageOrderValue = totalRevenue.divide(
                        BigDecimal.valueOf(completedOrders), 2, RoundingMode.HALF_UP
                );
            }

            MonthlyOrdersReportDTO report = new MonthlyOrdersReportDTO(
                    year,
                    month,
                    (long) orders.size(),
                    totalRevenue,
                    uniqueCustomers,
                    averageOrderValue,
                    orders
            );

            log.info("Monthly report generated successfully: {} orders, revenue: {}",
                    orders.size(), totalRevenue);
            return report;

        } catch (Exception e) {
            log.error("Error generating monthly orders report for {}/{}", month, year, e);
            throw new RuntimeException("Failed to generate monthly orders report", e);
        }
    }
}
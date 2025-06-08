package org.example.bookshop.controller;

import org.example.bookshop.dto.*;
import org.example.bookshop.service.ReportingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Collections;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    private static final Logger log = LoggerFactory.getLogger(ReportingController.class);
    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<LowStockReportDTO>> getLowStockProducts() {
        try {
            log.info("Requesting low stock products report");
            List<LowStockReportDTO> report = reportingService.getLowStockProducts();
            log.info("Low stock report returned {} products", report.size());
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            log.error("Error in low-stock endpoint", e);
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/monthly-orders")
    public ResponseEntity<MonthlyOrdersReportDTO> getCurrentMonthOrders() {
        try {
            log.info("Requesting monthly orders report");
            MonthlyOrdersReportDTO report = reportingService.getCurrentMonthOrdersReport();
            log.info("Monthly orders report generated successfully");
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            log.error("Error in monthly-orders endpoint", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
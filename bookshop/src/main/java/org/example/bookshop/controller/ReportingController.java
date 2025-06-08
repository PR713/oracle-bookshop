//package org.example.bookshop.controller;
//
//import org.example.bookshop.dto.*;
//import org.example.bookshop.service.ReportingService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/reports")
//public class ReportingController {
//
//    private final ReportingService reportingService;
//
//    public ReportingController(ReportingService reportingService) {
//        this.reportingService = reportingService;
//    }
//
//    @GetMapping("/top-products")
//    public ResponseEntity<List<ProductSalesReportDTO>> getTopSellingProducts() {
//        try {
//            List<ProductSalesReportDTO> report = reportingService.getTopSellingProducts();
//            return ResponseEntity.ok(report);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//
//    @GetMapping("/monthly-sales")
//    public ResponseEntity<MonthlySalesReportDTO> getCurrentMonthSales() {
//        try {
//            MonthlySalesReportDTO report = reportingService.getCurrentMonthSales();
//            return ResponseEntity.ok(report);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//
//    @GetMapping("/low-stock")
//    public ResponseEntity<List<LowStockReportDTO>> getLowStockProducts() {
//        try {
//            List<LowStockReportDTO> report = reportingService.getLowStockProducts();
//            return ResponseEntity.ok(report);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//
//    @GetMapping("/dashboard")
//    public ResponseEntity<DashboardDTO> getDashboard() {
//        try {
//            DashboardDTO dashboard = reportingService.getDashboardStats();
//            return ResponseEntity.ok(dashboard);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//}
package org.example.bookshop.controller;

import org.example.bookshop.entity.Order;
import org.example.bookshop.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<Order> createPurchase(
            @RequestParam Long customerId,
            @RequestBody Map<Long, Integer> productQuantities) {
        try {
            Order order = purchaseService.purchaseProducts(customerId, productQuantities);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
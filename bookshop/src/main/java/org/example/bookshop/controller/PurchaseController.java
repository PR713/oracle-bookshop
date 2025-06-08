package org.example.bookshop.controller;

import jakarta.validation.Valid;
import org.example.bookshop.dto.PurchaseRequestDTO;
import org.example.bookshop.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<String> createPurchase(@Valid @RequestBody PurchaseRequestDTO purchaseRequest) {
        try {
            purchaseService.purchaseProducts(purchaseRequest);
            return ResponseEntity.ok("Purchase completed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Purchase failed: " + e.getMessage());
        }
    }

}
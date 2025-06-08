package org.example.bookshop.controller;

import org.example.bookshop.dto.PurchaseRequestDTO;
import org.example.bookshop.dto.OrderDTO;
import org.example.bookshop.dto.PaymentDTO;
import org.example.bookshop.entity.Order;
import org.example.bookshop.service.PurchaseService;
import org.example.bookshop.service.OrderService;
import org.example.bookshop.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/purchase")
@CrossOrigin(origins = "*")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    public PurchaseController(PurchaseService purchaseService,
                              OrderService orderService,
                              PaymentService paymentService) {
        this.purchaseService = purchaseService;
        this.orderService = orderService;
        this.paymentService = paymentService;
    }


    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody PurchaseRequestDTO request) {
        try {
            Order order = purchaseService.purchaseProducts(request);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Order creation failed: " + e.getMessage());
        }
    }

    @PostMapping("/pay/{orderId}")
    public ResponseEntity<?> payForOrder(@PathVariable Long orderId) {
        try {
            OrderDTO order = purchaseService.processPayment(orderId);
            return ResponseEntity.ok("Payment successful. Order status: " + order.getOrderStatus());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Payment failed: " + e.getMessage());
        }
    }

    @PostMapping("/ship/{orderId}")
    public ResponseEntity<?> shipOrder(@PathVariable Long orderId, @RequestParam Long shipperId) {
        try {
            purchaseService.startShipping(orderId, shipperId);
            return ResponseEntity.ok("Order shipped. Tracking information updated.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Shipping failed: " + e.getMessage());
        }
    }

    @PostMapping("/complete/{orderId}")
    public ResponseEntity<?> completeOrder(@PathVariable Long orderId) {
        try {
            purchaseService.completeOrder(orderId);
            return ResponseEntity.ok("Order completed successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Order completion failed: " + e.getMessage());
        }
    }


    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        try {
            purchaseService.cancelOrder(orderId);
            return ResponseEntity.ok("Order cancelled successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cancellation failed: " + e.getMessage());
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderStatus(@PathVariable Long orderId) {
        try {
            OrderDTO order = orderService.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/order/{orderId}/payments")
    public ResponseEntity<?> getOrderPayments(@PathVariable Long orderId) {
        try {
            List<PaymentDTO> payments = paymentService.findByOrderId(orderId);
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @PostMapping("/retry-payment/{orderId}")
    public ResponseEntity<?> retryPayment(@PathVariable Long orderId) {
        try {
            OrderDTO order = purchaseService.retryPayment(orderId);
            return ResponseEntity.ok("Payment retry successful. Order status: " + order.getOrderStatus());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Payment retry failed: " + e.getMessage());
        }
    }

}
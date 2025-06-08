package org.example.bookshop.service;

import org.example.bookshop.dto.PurchaseRequestDTO;
import org.example.bookshop.dto.PurchaseItemDTO;
import org.example.bookshop.entity.*;
import org.example.bookshop.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.LockModeType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PurchaseService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderDetailRepository orderDetailRepository;

    public PurchaseService(ProductRepository productRepository,
                           CustomerRepository customerRepository,
                           OrderRepository orderRepository,
                           PaymentRepository paymentRepository,
                           OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    /**
     * Główna metoda zakupu z pessimistic locking
     * Zapewnia bezpieczeństwo w środowisku wielowątkowym
     */
    @Transactional(rollbackFor = Exception.class)
    public Order purchaseProducts(PurchaseRequestDTO request) {
        // 1. Sprawdź czy klient istnieje
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found: " + request.getCustomerId()));

        // 2. Utwórz zamówienie
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.from(LocalDateTime.now()));
        order.setOrderStatus("PROCESSING");
        order = orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;

        // 3. Przetwórz każdy produkt z PESSIMISTIC LOCK
        for (PurchaseItemDTO item : request.getItems()) {
            // PESSIMISTIC LOCK - blokuje rekord w bazie danych
            Product product = entityManager.find(Product.class, item.getProductId(), LockModeType.PESSIMISTIC_WRITE);

            if (product == null) {
                throw new RuntimeException("Product not found: " + item.getProductId());
            }

            // Sprawdź dostępność magazynową
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException(
                        String.format("Insufficient stock for product %s. Available: %d, Requested: %d",
                                product.getName(), product.getStock(), item.getQuantity()));
            }

            // Zmniejsz stan magazynowy
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            // Dodaj szczegóły zamówienia
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setUnitPrice(product.getPrice());
            orderDetailRepository.save(orderDetail);

            // Oblicz kwotę
            totalAmount = totalAmount.add(
                    BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }

        // 4. Utwórz płatność
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentDate(LocalDate.from(LocalDateTime.now()));
        payment.setPaymentStatus("COMPLETED");
        paymentRepository.save(payment);

        // 5. Zaktualizuj status zamówienia
        order.setOrderStatus("COMPLETED");
        return orderRepository.save(order);
    }
}
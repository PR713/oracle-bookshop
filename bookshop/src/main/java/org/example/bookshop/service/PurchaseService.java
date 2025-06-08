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
     */
    @Transactional(rollbackFor = Exception.class)
    public Order purchaseProducts(PurchaseRequestDTO request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found: " + request.getCustomerId()));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.from(LocalDateTime.now()));
        order.setOrderStatus("PROCESSING");
        order = orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (PurchaseItemDTO item : request.getItems()) {
            // PESSIMISTIC LOCK - blokujemy rekord w bazie danych
            Product product = entityManager.find(Product.class, item.getProductId(), LockModeType.PESSIMISTIC_WRITE);

            if (product == null) {
                throw new RuntimeException("Product not found: " + item.getProductId());
            }

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException(
                        String.format("Insufficient stock for product %s. Available: %d, Requested: %d",
                                product.getName(), product.getStock(), item.getQuantity()));
            }

            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setUnitPrice(product.getPrice());
            orderDetailRepository.save(orderDetail);

            totalAmount = totalAmount.add(
                    BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentDate(LocalDate.from(LocalDateTime.now()));
        payment.setPaymentStatus("COMPLETED");
        paymentRepository.save(payment);

        order.setOrderStatus("COMPLETED");
        return orderRepository.save(order);
    }
}
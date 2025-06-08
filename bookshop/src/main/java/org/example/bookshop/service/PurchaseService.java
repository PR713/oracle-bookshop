package org.example.bookshop.service;

import org.example.bookshop.dto.PurchaseRequestDTO;
import org.example.bookshop.dto.PurchaseItemDTO;
import org.example.bookshop.dto.OrderDTO;
import org.example.bookshop.dto.PaymentDTO;
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
import java.util.List;

@Service
public class PurchaseService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ProductService productService;
    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final OrderDetailRepository orderDetailRepository;
    private final ShipperRepository shipperRepository;

    public PurchaseService(ProductService productService,
                           CustomerRepository customerRepository,
                           OrderService orderService,
                           PaymentService paymentService,
                           OrderDetailRepository orderDetailRepository,
                           ShipperRepository shipperRepository) {
        this.productService = productService;
        this.customerRepository = customerRepository;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.orderDetailRepository = orderDetailRepository;
        this.shipperRepository = shipperRepository;
    }

    /**
     * pessimistic locking - transakcja zakupu z kontrolą równoczesnego dostępu
     */
    @Transactional(rollbackFor = Exception.class)
    public Order purchaseProducts(PurchaseRequestDTO request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found: " + request.getCustomerId()));

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerId(customer.getCustomerID());
        orderDTO.setOrderDate(LocalDate.from(LocalDateTime.now()));
        orderDTO.setOrderStatus("NEW");
        orderDTO.setShipVia(null);

        OrderDTO savedOrderDTO = orderService.save(orderDTO);
        Order order = orderService.convertToEntity(savedOrderDTO);

        BigDecimal totalAmount = BigDecimal.ZERO;

        try {
            for (PurchaseItemDTO item : request.getItems()) {
                // PESSIMISTIC LOCK - blokujemy rekord w bazie danych dla race condition
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
                productService.save(product);

                OrderDetail orderDetail = new OrderDetail();
                OrderDetailId orderDetailId = new OrderDetailId();
                orderDetailId.setOrderID(order.getOrderID());
                orderDetailId.setProductID(product.getProductID());

                orderDetail.setId(orderDetailId);
                orderDetail.setOrder(order);
                orderDetail.setProduct(product);
                orderDetail.setQuantity(item.getQuantity());
                orderDetail.setUnitPrice(product.getPrice());
                orderDetailRepository.save(orderDetail);

                totalAmount = totalAmount.add(
                        BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(item.getQuantity()))
                );
            }


            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setOrderId(order.getOrderID());
            paymentDTO.setPaymentDate(LocalDate.from(LocalDateTime.now()));
            paymentDTO.setPaymentStatus("NEW");
            paymentService.save(paymentDTO);

        } catch (Exception e) {
            orderDTO.setOrderStatus("CANCELLED");
            orderService.save(orderDTO);
            throw e;
        }

        return order;
    }


    @Transactional(rollbackFor = Exception.class)
    public OrderDTO processPayment(Long orderId) {
        OrderDTO orderDTO = orderService.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));


        if (!"NEW".equals(orderDTO.getOrderStatus())) {
            throw new RuntimeException("Only NEW orders can be paid");
        }

        List<PaymentDTO> payments = paymentService.findByOrderId(orderId);

        PaymentDTO payment = payments.stream()
                .filter(p -> "NEW".equals(p.getPaymentStatus()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No pending payment found"));


        boolean paymentSuccessful = processPaymentSimulation();

        if (paymentSuccessful) {
            payment.setPaymentStatus("PAID");
            paymentService.save(payment);

            orderDTO.setOrderStatus("PROCESSING");
            return orderService.save(orderDTO);
        } else {
            payment.setPaymentStatus("FAILED");
            paymentService.save(payment);
            throw new RuntimeException("Payment failed");
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public OrderDTO startShipping(Long orderId, Long shipperId) {
        OrderDTO orderDTO = orderService.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        if (!"PROCESSING".equals(orderDTO.getOrderStatus())) {
            throw new RuntimeException("Only PROCESSING orders can be shipped");
        }

        shipperRepository.findById(shipperId)
                .orElseThrow(() -> new RuntimeException("Shipper not found: " + shipperId));

        orderDTO.setShipVia(shipperId);
        return orderService.save(orderDTO);

    }


    @Transactional(rollbackFor = Exception.class)
    public OrderDTO completeOrder(Long orderId) {
        OrderDTO orderDTO = orderService.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        if (!"PROCESSING".equals(orderDTO.getOrderStatus())) {
            throw new RuntimeException("Only PROCESSING orders can be completed");
        }

        orderDTO.setOrderStatus("COMPLETED");
        return orderService.save(orderDTO);
    }


    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Long orderId) {
        OrderDTO orderDTO = orderService.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        if ("COMPLETED".equals(orderDTO.getOrderStatus())) {
            throw new RuntimeException("Completed orders cannot be cancelled");
        }

        // Przywrócenie stanu magazynowego
        Order order = entityManager.find(Order.class, orderId);
        if (order != null && order.getOrderDetails() != null) {
            for (OrderDetail detail : order.getOrderDetails()) {
                Product product = entityManager.find(Product.class,
                        detail.getProduct().getProductID(), LockModeType.PESSIMISTIC_WRITE);
                product.setStock(product.getStock() + detail.getQuantity());
                productService.save(product);
            }
        }

        orderDTO.setOrderStatus("CANCELLED");
        orderService.save(orderDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderDTO retryPayment(Long orderId) {
        OrderDTO orderDTO = orderService.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        if (!"NEW".equals(orderDTO.getOrderStatus())) {
            throw new RuntimeException("Only NEW orders can have payment retried");
        }

        List<PaymentDTO> payments = paymentService.findByOrderId(orderId);
        PaymentDTO failedPayment = payments.stream()
                .filter(p -> "FAILED".equals(p.getPaymentStatus()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No failed payment found"));

        failedPayment.setPaymentStatus("NEW");
        paymentService.save(failedPayment);

        return processPayment(orderId);
    }


    private boolean processPaymentSimulation() {
        return Math.random() > 0.15;
    }
}
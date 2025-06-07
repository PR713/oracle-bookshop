package org.example.bookshop.service;

import jakarta.transaction.Transactional;
import org.example.bookshop.entity.*;
import org.example.bookshop.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class PurchaseService {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderService orderService;

    public PurchaseService(ProductRepository productRepository,
                           CustomerRepository customerRepository,
                           OrderRepository orderRepository,
                           PaymentRepository paymentRepository,
                           OrderDetailRepository orderDetailRepository, OrderService orderService) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.orderService = orderService;
    }

    @Transactional
    public void purchaseProducts(Long customerId, Map<Long, Integer> productQuantities) {
        Map<Product, Integer> productsToOrder = new HashMap<>();

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            Product product = productRepository.findProductForUpdate(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Product not found" + entry.getKey()));

            if (product.getStock() < entry.getValue()) {
                throw new RuntimeException("Product has no enough stock" + product.getName());
            }

            productsToOrder.put(product, entry.getValue());
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found" + customerId));

        Order order = new Order();

        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("NEW");
        orderService.save(order);

        for (Map.Entry<Product, Integer> entry : productsToOrder.entrySet()) {
            Product product = entry.getKey();
            product.setStock(product.getStock() - entry.getValue());
            productRepository.save(product);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(productQuantities.get(product.getProductID()));
            orderDetail.setOrder(order);
            orderDetail.setUnitPrice(product.getPrice());
            orderDetail.setDiscount(0.2F);
            orderDetailRepository.save(orderDetail);
        }


        Payment payment = new Payment();
        payment.setPaymentDate(LocalDate.now());
        payment.setOrder(order);
        payment.setPaymentStatus("NEW");
        paymentRepository.save(payment);
    }

}

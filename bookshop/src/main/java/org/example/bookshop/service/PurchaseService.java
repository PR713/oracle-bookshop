package org.example.bookshop.service;

import jakarta.transaction.Transactional;
import org.example.bookshop.entity.Order;
import org.example.bookshop.entity.Product;
import org.example.bookshop.repository.CustomerRepository;
import org.example.bookshop.repository.OrderRepository;
import org.example.bookshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PurchaseService {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public PurchaseService(ProductRepository productRepository,
                           CustomerRepository customerRepository,
                           OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void purchaseProduct(Long productId, Long customerId) {
        Product product = productRepository.findProductForUpdate(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found")); // blokada!

        if (product.getStock() <= 0) {
            throw new RuntimeException("Out of stock");
        }

        product.setStock(product.getStock() - 1);

        Order order = new Order();
        order.setCustomer(customerRepository.findById(customerId).orElseThrow());
        order.setProduct(product);
        order.setOrderDate(LocalDate.now());
        orderRepository.save(order);

        // payment, etc...
    }

}

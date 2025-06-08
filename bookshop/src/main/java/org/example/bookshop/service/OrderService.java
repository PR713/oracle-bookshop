package org.example.bookshop.service;

import org.example.bookshop.dto.OrderDTO;
import org.example.bookshop.entity.Customer;
import org.example.bookshop.entity.Order;
import org.example.bookshop.entity.Shipper;
import org.example.bookshop.repository.CustomerRepository;
import org.example.bookshop.repository.OrderRepository;
import org.example.bookshop.repository.ShipperRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ShipperRepository shipperRepository;

    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        ShipperRepository shipperRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.shipperRepository = shipperRepository;
    }

    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<OrderDTO> findById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO);
    }

    public OrderDTO save(OrderDTO orderDTO) {
        Order order = convertToEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderID());
        dto.setCustomerId(order.getCustomer().getCustomerID());
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderStatus(order.getOrderStatus());
        if (order.getShipVia() != null) {
            dto.setShipVia(order.getShipVia().getShipperID());
        }
        return dto;
    }

    Order convertToEntity(OrderDTO dto) {
        Order order = new Order();
        if (dto.getOrderId() != null) {
            order.setOrderID(dto.getOrderId());
        }

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found: " + dto.getCustomerId()));
        order.setCustomer(customer);

        order.setOrderDate(dto.getOrderDate());
        order.setOrderStatus(dto.getOrderStatus());

        if (dto.getShipVia() != null) {
            Shipper shipper = shipperRepository.findById(dto.getShipVia())
                    .orElseThrow(() -> new RuntimeException("Shipper not found: " + dto.getShipVia()));
            order.setShipVia(shipper);
        }

        return order;
    }
}
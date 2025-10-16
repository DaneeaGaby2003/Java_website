package com.example.orders.service;

import com.example.orders.domain.Order;
import com.example.orders.domain.OrderItem;
import com.example.orders.domain.OrderStatus;
import com.example.orders.repository.OrderRepository;
import com.example.orders.web.dto.CreateOrderRequest;
import com.example.orders.web.dto.OrderItemRequest;
import com.example.orders.web.dto.UpdateOrderRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order create(CreateOrderRequest req) {
        Order order = new Order();
        order.setCustomerName(req.customerName());
        order.setCustomerEmail(req.customerEmail());
        req.items().forEach(i -> order.addItem(map(i)));
        return orderRepository.save(order);
    }

    public List<Order> list() {
        return orderRepository.findAll();
    }

    public Order get(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order update(UUID id, UpdateOrderRequest req) {
        Order order = get(id);

        if (req.customerName() != null) order.setCustomerName(req.customerName());
        if (req.customerEmail() != null) order.setCustomerEmail(req.customerEmail());

        if (req.items() != null) {
            order.clearItems();
            req.items().forEach(i -> order.addItem(map(i)));
        }

        if (req.status() != null) {
            order.setStatus(OrderStatus.valueOf(req.status()));
        }

        return orderRepository.save(order);
    }

    public void delete(UUID id) {
        orderRepository.deleteById(id);
    }

    // ---- Mapeo usando setters (no requiere constructor en OrderItem)
    private OrderItem map(OrderItemRequest r) {
        OrderItem item = new OrderItem();     // usa constructor vacÃƒÂ­o
        item.setSku(r.sku());
        item.setName(r.name());
        item.setQuantity(r.quantity());
        item.setUnitPrice(r.unitPrice());
        return item;
    }
}

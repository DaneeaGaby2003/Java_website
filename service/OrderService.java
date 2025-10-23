package com.example.orders.service;

import com.example.orders.domain.Order;
import com.example.orders.web.dto.CreateOrderRequest;
import com.example.orders.web.dto.UpdateOrderRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    public Order create(CreateOrderRequest req) {
        // TODO: tu lógica de persistencia
        throw new UnsupportedOperationException("Implement create()");
    }

    public Order getById(UUID id) {
        // TODO
        throw new UnsupportedOperationException("Implement getById()");
    }

    public List<Order> list() {
        // TODO
        throw new UnsupportedOperationException("Implement list()");
    }

    public Order update(UUID id, UpdateOrderRequest req) {
        // TODO
        throw new UnsupportedOperationException("Implement update()");
    }

    public void delete(UUID id) {
        // TODO
        throw new UnsupportedOperationException("Implement delete()");
    }
}

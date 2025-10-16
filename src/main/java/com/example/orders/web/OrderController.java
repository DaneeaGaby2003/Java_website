package com.example.orders.web;

import com.example.orders.domain.Order;
import com.example.orders.service.OrderService;
import com.example.orders.web.dto.CreateOrderRequest;
import com.example.orders.web.dto.UpdateOrderRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /** Create a new order */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.create(request);
    }

    /** Retrieve all orders */
    @GetMapping
    public List<Order> list() {
        return orderService.list();
    }

    /** Retrieve a single order by id */
    @GetMapping("/{id}")
    public Order get(@PathVariable UUID id) {
        return orderService.get(id);
    }

    /** Update an order by id */
    @PutMapping("/{id}")
    public Order update(@PathVariable UUID id, @RequestBody UpdateOrderRequest request) {
        return orderService.update(id, request);
    }

    /** Delete an order by id */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        orderService.delete(id);
    }
}


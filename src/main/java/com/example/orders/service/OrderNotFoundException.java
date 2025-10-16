package com.example.orders.service;

import java.util.UUID;

/**
 * Thrown when an Order is not found by id.
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(UUID id) {
        super("Order not found: " + id);
    }
}

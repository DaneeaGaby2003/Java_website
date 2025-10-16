package com.example.orders.web.dto;

import java.util.List;

public record UpdateOrderRequest(
        String customerName,
        String customerEmail,
        List<OrderItemRequest> items,
        String status
) {}

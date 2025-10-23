package com.example.orders.web.dto;

import com.example.orders.domain.OrderStatus;
import jakarta.validation.constraints.Email;

import java.math.BigDecimal;
import java.util.List;

public record UpdateOrderRequest(
        String customerName,
        @Email String customerEmail,
        OrderStatus status,
        List<OrderItemRequest> items,
        BigDecimal ignoreTotal // opcional; puedes quitarlo si no lo usas
) {}

package com.example.orders.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderRequest(
        @NotBlank String customerName,
        @Email @NotBlank String customerEmail,
        @NotEmpty List<OrderItemRequest> items
) {}

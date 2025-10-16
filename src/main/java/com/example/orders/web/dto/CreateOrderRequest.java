package com.example.orders.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateOrderRequest(
        @NotBlank String customerName,
        @NotBlank @Email String customerEmail,
        @NotNull List<@NotNull OrderItemRequest> items
) {}

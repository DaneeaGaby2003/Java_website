package com.example.orders.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record OrderItemRequest(
        @NotBlank String sku,
        @NotBlank String name,
        @Positive Integer quantity,
        @Positive BigDecimal unitPrice
) {}

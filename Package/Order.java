package com.example.orders.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record Order(
        UUID id,
        String customerEmail,
        BigDecimal amount,
        String currency,
        OffsetDateTime createdAt
) {}

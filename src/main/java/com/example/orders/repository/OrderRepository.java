package com.example.orders.repository;

import com.example.orders.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository interface for Order persistence operations.
 */
public interface OrderRepository extends JpaRepository<Order, UUID> {}

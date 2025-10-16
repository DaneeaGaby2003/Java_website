package com.example.orders.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Order entity representing a customer's order in the online store.
 */
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    @Size(max = 120)
    private String customerName;

    @NotBlank
    @Email
    @Size(max = 160)
    private String customerEmail;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(precision = 12, scale = 2, nullable = false)
    @PositiveOrZero
    private BigDecimal totalAmount = BigDecimal.ZERO;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();

    @PrePersist
    void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public void addItem(OrderItem item) {
        item.setOrder(this);
        items.add(item);
        recalcTotal();
    }

    public void clearItems() {
        items.forEach(i -> i.setOrder(null));
        items.clear();
        recalcTotal();
    }

    public void recalcTotal() {
        totalAmount = items.stream()
                .map(OrderItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}

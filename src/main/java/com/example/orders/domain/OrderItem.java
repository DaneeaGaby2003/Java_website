package com.example.orders.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * OrderItem entity representing each line item in an order.
 */
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    @Size(max = 160)
    private String sku;

    @NotBlank
    @Size(max = 200)
    private String name;

    @Positive
    private int quantity;

    @Positive
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public BigDecimal getLineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}

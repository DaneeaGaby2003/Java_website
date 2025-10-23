package com.example.orders.service;

import com.example.orders.domain.Order;
import com.example.orders.domain.OrderStatus;
import com.example.orders.repository.OrderRepository;
import com.example.orders.web.dto.CreateOrderRequest;
import com.example.orders.web.dto.OrderItemRequest;
import com.example.orders.web.dto.UpdateOrderRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTest {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderRepository repository;

    @Test
    void create_and_getById_ok() {
        // Arrange
        var req = new CreateOrderRequest(
                "Ada Lovelace",
                "ada@example.com",
                List.of(new OrderItemRequest("SKU-1", "Teclado mecánico", 2, new BigDecimal("199.99")))
        );

        // Act
        Order created = service.create(req);

        // Assert
        assertNotNull(created.getId());
        assertEquals("Ada Lovelace", created.getCustomerName());
        assertEquals("ada@example.com", created.getCustomerEmail());
        assertEquals(OrderStatus.PENDING, created.getStatus());
        assertEquals(0, new BigDecimal("399.98").compareTo(created.getTotalAmount())); // 199.99 * 2
        assertEquals(1, created.getItems().size());

        // getById
        Order byId = service.getById(created.getId());
        assertEquals(created.getId(), byId.getId());
    }

    @Test
    void update_replaces_fields_and_items_ok() {
        // given
        var created = service.create(new CreateOrderRequest(
                "John Doe",
                "john@doe.com",
                List.of(new OrderItemRequest("SKU-1", "Producto inicial", 1, new BigDecimal("10.00")))
        ));

        // when: cambiamos nombre, status y items (reemplaza)
        var update = new UpdateOrderRequest(
                "John D.",                // customerName
                "john.d@doe.com",         // customerEmail
                OrderStatus.PAID,         // status
                List.of(new OrderItemRequest("SKU-2", "Mouse gamer", 3, new BigDecimal("20.00"))), // items
                null                      // ignoreTotal (no lo usamos)
        );

        Order updated = service.update(created.getId(), update);

        // then
        assertEquals("John D.", updated.getCustomerName());
        assertEquals("john.d@doe.com", updated.getCustomerEmail());
        assertEquals(OrderStatus.PAID, updated.getStatus());
        assertEquals(1, updated.getItems().size());
        assertEquals("SKU-2", updated.getItems().get(0).getSku());
        assertEquals(0, new BigDecimal("60.00").compareTo(updated.getTotalAmount())); // 3 * 20.00
    }

    @Test
    void delete_then_notFound() {
        var created = service.create(new CreateOrderRequest(
                "Del Me",
                "del@me.com",
                List.of(new OrderItemRequest("SKU-DEL", "Descartable", 1, new BigDecimal("5.00")))
        ));

        service.delete(created.getId());

        assertFalse(repository.existsById(created.getId()));
        assertThrows(OrderNotFoundException.class,
                () -> service.getById(created.getId()));
    }
}

package com.example.orders.service;

import com.example.orders.web.dto.CreateOrderRequest;
import com.example.orders.web.dto.OrderItemRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    @Disabled("Activa cuando implementes OrderService.create()")
    void create_ok() {
        var svc = new OrderService(); // si usas repo, usa @SpringBootTest y @Autowired
        var req = new CreateOrderRequest(
                "user@meli.com",
                BigDecimal.valueOf(199.99),
                "MXN",
                List.of(new OrderItemRequest("SKU-1", 2))
        );

        var o = svc.create(req);
        assertNotNull(o.id());
        assertEquals("user@meli.com", o.customerEmail());
    }
}

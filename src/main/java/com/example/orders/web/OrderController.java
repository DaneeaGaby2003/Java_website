package com.example.orders.web;

import com.example.orders.domain.Order;
import com.example.orders.service.OrderService;
import com.example.orders.web.dto.CreateOrderRequest;
import com.example.orders.web.dto.UpdateOrderRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    /** GET /api/orders - Listar todas las órdenes */
    @GetMapping
    public List<Order> list() {
        return service.list();
    }

    /** POST /api/orders - Crear una orden */
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody @Valid CreateOrderRequest req) {
        var created = service.create(req);
        return ResponseEntity
                .created(URI.create("/api/orders/" + created.getId()))
                .body(created);
    }

    /** GET /api/orders/{id} - Obtener una orden por ID */
    @GetMapping("/{id}")
    public Order getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    /** PUT /api/orders/{id} - Actualizar una orden */
    @PutMapping("/{id}")
    public Order update(@PathVariable UUID id, @RequestBody @Valid UpdateOrderRequest req) {
        return service.update(id, req);
    }

    /** DELETE /api/orders/{id} - Eliminar una orden */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

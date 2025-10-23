package com.example.orders.service;

import com.example.orders.domain.Order;
import com.example.orders.domain.OrderItem;
import com.example.orders.domain.OrderStatus;
import com.example.orders.repository.OrderRepository;
import com.example.orders.web.dto.CreateOrderRequest;
import com.example.orders.web.dto.OrderItemRequest;
import com.example.orders.web.dto.UpdateOrderRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    /** Listar todas las órdenes */
    @Transactional(readOnly = true)
    public List<Order> list() {
        return repository.findAll();
    }

    /** Crear una orden */
    @Transactional
    public Order create(CreateOrderRequest req) {
        var order = new Order();
        order.setCustomerName(req.customerName());
        order.setCustomerEmail(req.customerEmail());
        order.setStatus(OrderStatus.PENDING);

        // Mapear items del DTO a entidades
        var items = (req.items() == null) ? List.<OrderItemRequest>of() : req.items();
        for (OrderItemRequest i : items) {
            var item = new OrderItem();
            item.setOrder(order);
            item.setSku(i.sku());
            item.setName(i.name());
            item.setQuantity(i.quantity());
            item.setUnitPrice(i.unitPrice());
            order.getItems().add(item);
        }

        order.recalcTotal();
        return repository.save(order);
    }

    /** Obtener una orden por ID (o lanzar 404) */
    @Transactional(readOnly = true)
    public Order getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    /** Actualizar una orden existente (reemplaza items) */
    @Transactional
    public Order update(UUID id, UpdateOrderRequest req) {
        var order = getById(id);

        order.setCustomerName(req.customerName());
        order.setCustomerEmail(req.customerEmail());
        order.setStatus(req.status());

        // Reemplazar items
        order.getItems().clear();
        var items = (req.items() == null) ? List.<OrderItemRequest>of() : req.items();
        for (OrderItemRequest i : items) {
            var item = new OrderItem();
            item.setOrder(order);
            item.setSku(i.sku());
            item.setName(i.name());
            item.setQuantity(i.quantity());
            item.setUnitPrice(i.unitPrice());
            order.getItems().add(item);
        }

        order.recalcTotal();
        return repository.save(order);
    }

    /** Eliminar una orden */
    @Transactional
    public void delete(UUID id) {
        var order = getById(id);
        repository.delete(order);
    }
}


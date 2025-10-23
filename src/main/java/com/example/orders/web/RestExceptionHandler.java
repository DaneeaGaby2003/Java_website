package com.example.orders.web;

import com.example.orders.service.OrderNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> Map.of("field", e.getField(), "error", e.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(
                Map.of("message", "Validation failed", "errors", errors)
        );
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleNotFound(OrderNotFoundException ex) {
        return ResponseEntity.status(404).body(
                Map.of("message", ex.getMessage())
        );
    }
}


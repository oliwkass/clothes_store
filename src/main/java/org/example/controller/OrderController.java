package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Order;
import org.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // POST-запрос для оформления заказа
// POST-запрос для оформления заказа через JSON Body
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody org.example.dto.OrderRequest request) {
        // Передаем весь объект запроса в сервис
        Order newOrder = orderService.createOrder(request);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }
}
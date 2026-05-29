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
    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestParam Long userId,
            @RequestParam List<Long> productIds) {

        Order newOrder = orderService.createOrder(userId, productIds);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }
}
package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.ResourceNotFoundException;
import org.example.model.Order;
import org.example.model.Product;
import org.example.model.User;
import org.example.repository.OrderRepository;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // Автоматически создаст конструктор для внедрения репозиториев
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @org.springframework.transaction.annotation.Transactional
    public Order createOrder(org.example.dto.OrderRequest request) {
        // 1. Ищем пользователя по userId из DTO
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + request.getUserId() + " not found"));

        List<Product> orderProducts = new ArrayList<>();
        double calculatedTotal = 0.0;

        // 2. Обходим список позиций (items) из запроса
        for (org.example.dto.OrderItemRequest item : request.getItems()) {
            // Ищем продукт в базе
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + item.getProductId() + " not found"));

            int requestedQuantity = item.getQuantity();

            // Валидация: не пытается ли пользователь заказать 0 или отрицательное количество?
            if (requestedQuantity <= 0) {
                throw new IllegalArgumentException("Quantity for product '" + product.getName() + "' must be greater than 0");
            }

            // Проверяем, хватает ли товара на складе для этого шага
            if (product.getStockQuantity() == null || product.getStockQuantity() < requestedQuantity) {
                throw new ResourceNotFoundException("Not enough stock for product '" + product.getName() +
                        "'. Requested: " + requestedQuantity + ", Available: " + product.getStockQuantity());
            }

            // Списываем нужное количество со склада сразу
            product.setStockQuantity(product.getStockQuantity() - requestedQuantity);

            // Считаем общую стоимость: цена продукта * количество штук
            calculatedTotal += product.getPrice() * requestedQuantity;

            // Добавляем продукт в список заказа столько раз, сколько его купили (чтобы сохранить связь в БД)
            for (int i = 0; i < requestedQuantity; i++) {
                orderProducts.add(product);
            }
        }

        // 3. Формируем и сохраняем заказ
        Order order = new Order();
        order.setUser(user);
        order.setProducts(orderProducts);
        order.setOrderDate(java.time.LocalDateTime.now());
        order.setStatus("CREATED");
        order.setTotalPrice(calculatedTotal);

        return orderRepository.save(order);
    }
}
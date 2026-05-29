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
import java.util.List;

@Service
@RequiredArgsConstructor // Автоматически создаст конструктор для внедрения репозиториев
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Order createOrder(Long userId, List<Long> productIds) {
        // 1. Ищем пользователя. Если его нет — выкидываем нашу красивую 404 ошибку
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        // 2. Достаем все выбранные продукты из базы по их ID
        List<Product> products = productRepository.findAllById(productIds);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for the given IDs");
        }

        // 3. Создаем новый заказ и заполняем данные
        Order order = new Order();
        order.setUser(user);
        order.setProducts(products);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("CREATED");

        // 4. Сохраняем заказ в базу
        return orderRepository.save(order);
    }
}
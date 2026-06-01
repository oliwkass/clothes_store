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
    public Order createOrder(Long userId, List<Long> productIds) {
        // 1. Ищем пользователя
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Сюда мы будем складывать купленные продукты (включая дубликаты!)
        List<Product> orderProducts = new ArrayList<>();
        double calculatedTotal = 0.0;

        // 2. Обходим исходный список ID, который пришел от пользователя [2, 2, 2]
        for (Long productId : productIds) {
            // Ищем продукт в базе по конкретному ID
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + productId + " not found"));

            // Проверяем склад
            if (product.getStockQuantity() == null || product.getStockQuantity() <= 0) {
                throw new ResourceNotFoundException("Product '" + product.getName() + "' is out of stock!");
            }

            // Списываем строго 1 единицу для текущего ID из списка
            product.setStockQuantity(product.getStockQuantity() - 1);

            // Добавляем цену к общему чеку
            calculatedTotal += product.getPrice();

            // Кладем продукт в наш итоговый список для заказа
            orderProducts.add(product);
        }

        // 3. Создаем и сохраняем заказ
        Order order = new Order();
        order.setUser(user);
        order.setProducts(orderProducts); // Теперь здесь будут лежать все 3 куртки!
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("CREATED");
        order.setTotalPrice(calculatedTotal);

        return orderRepository.save(order);
    }
}
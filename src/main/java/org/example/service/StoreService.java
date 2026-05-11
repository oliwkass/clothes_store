package org.example.service;

import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.List;

@Service
public class StoreService {

    // 1. Оставляем только репозиторий. Список products здесь больше не нужен!
    private final ProductRepository productRepository;


    // 2. Добавляем правильный конструктор для связи со Spring
    public StoreService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        // Теперь просим данные у репозитория
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        System.out.println("Товар добавлен в базу с ID: " + savedProduct.getId());
    }

    public List<Product> findByCategory(String category) {
        // Делегируем поиск репозиторию
        return productRepository.findByCategoryIgnoreCase(category);
    }

    public boolean deleteProductById(Long id) {
        // Сначала проверяем существование
        boolean exists = productRepository.findAll().stream()
                .anyMatch(p -> p.getId().equals(id));

        if (exists) {
            productRepository.deleteById(id);
            System.out.println("✅ Товар с ID " + id + " успешно удален.");
            return true;
        } else {
            System.out.println("❌ Товар с ID " + id + " не найден.");
            return false;
        }
    }

    @PostConstruct
    public void initData() {
        addProduct(new Product(null, "Футболка", "Одежда", 1500.0, "L", 10, "Белый"));
        addProduct(new Product(null, "Джинсы", "Одежда", 3500.0, "M", 5, "Синий"));
    }
}
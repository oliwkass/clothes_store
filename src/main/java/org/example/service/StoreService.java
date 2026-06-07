package org.example.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.model.Category;
import org.example.model.Product;
import org.example.repository.CategoryRepository;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.example.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final org.example.repository.UserRepository userRepository;


    // Один чистый конструктор для внедрения зависимостей

    // Тот самый метод, который связывает Товар и Категорию
    public Product addProduct(Product product, String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    Category newCat = new Category();
                    newCat.setName(categoryName);
                    return categoryRepository.save(newCat);
                });

        product.setCategory(category);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> findByPriceRange(Double min, Double max) {
        return productRepository.findByPriceBetween(min, max);
    }

    public List<Product> findByCategory(String category) {
        // Вызываем обновленный метод репозитория
        return productRepository.findByCategory_NameIgnoreCase(category);
    }

    public Product updateProduct(Long id, Product details) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new org.example.exception.ResourceNotFoundException("Product with ID " + id + " not found"));
        product.setName(details.getName());
        product.setPrice(details.getPrice());
        product.setCategory(details.getCategory()); // Устанавливаем объект Category
        product.setStockQuantity(details.getStockQuantity());
        product.setColor(details.getColor());
        product.setSize(details.getSize());

        return productRepository.save(product);
    }

    public boolean deleteProductById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ВНИМАНИЕ: initData временно закомментируем или поправим,
    // так как старый конструктор Product(..., "Одежда", ...) больше не работает.

    @jakarta.annotation.PostConstruct
    public void initData() {
        // Проверяем, если в базе еще нет пользователей, создаем одного тестового
        if (userRepository.count() == 0) {
            org.example.model.User testUser = new org.example.model.User();
            testUser.setName("Тестовый Покупатель");
            testUser.setEmail("test@vibe.com");
            userRepository.save(testUser);
            System.out.println("=== ТЕСТОВЫЙ ПОЛЬЗОВАТЕЛЬ СОЗДАН С ID = 1 ===");
        }
    }

    public List<Product> getProductsByFilter(String name, Double minPrice, Double maxPrice) {
        if (name != null && !name.trim().isEmpty()) {
            return searchByName(name);
        } else if (minPrice != null && maxPrice != null) {
            return findByPriceRange(minPrice, maxPrice);
        } else {
            return getAllProducts();
        }
    }
}
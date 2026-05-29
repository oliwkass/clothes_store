package org.example.repository;

import org.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Ищет по имени продукта (содержит строку, игнорируя регистр)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Правильный поиск по имени КАТЕГОРИИ внутри объекта Category!
    List<Product> findByCategory_NameIgnoreCase(String categoryName);

    // Поиск по диапазону цен
    List<Product> findByPriceBetween(Double min, Double max);
}
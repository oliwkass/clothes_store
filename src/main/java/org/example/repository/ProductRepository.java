package org.example.repository;

import org.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Поиск по части имени (независимо от регистра)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Поиск товаров в диапазоне цен
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Product> findByCategoryIgnoreCase(String category);
}


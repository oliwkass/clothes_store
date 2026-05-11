package org.example.repository;

import org.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Нам даже не нужно писать методы findAll или save!
    // Spring Data JPA сделает это за нас.

    // Но если нужен поиск по категории, пишем так:
    List<Product> findByCategoryIgnoreCase(String category);
}
package org.example.service;

import org.example.model.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoreServiceTest {

    private StoreService service;
    private ProductRepository repository;

    @BeforeEach
    void setUp() {
        // Перед каждым тестом создаем чистый репозиторий и сервис
        repository = new InMemoryProductRepository();
        service = new StoreService(repository);
    }

    @Test
    void testAddProduct() {
        Product product = new Product(null, "Тест", "Категория", 100.0, "S", 1, "Черный");
        service.addProduct(product);

        assertEquals(1, service.getAllProducts().size());
        assertEquals(1L, service.getAllProducts().get(0).getId());
    }

    @Test
    void testFindByCategoryNotFound() {
        List<Product> result = service.findByCategory("Электроника");
        assertTrue(result.isEmpty());
    }
}
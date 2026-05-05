package org.example.service;

import org.example.model.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class StoreServiceTest {

    @Test
    void testFindByCategoryIgnoreCase() {
        StoreService service = new StoreService();

        // 1. ПОДГОТОВКА: Добавляем тестовый товар (убедись, что в StoreService есть такой метод)
        // Если метода addProduct еще нет, нам нужно его создать в StoreService
        service.addProduct(new Product(3L,"Футболка", "Одежда", 1500, "L", 1, "белый"));

        // 2. ДЕЙСТВИЕ: Ищем "одежда" маленькими буквами
        List<Product> result = service.findByCategory("Одежда");

        // 3. ПРОВЕРКА
        assertFalse(result.isEmpty(), "Список не должен быть пустым, если мы добавили товар");

        assertEquals("Одежда", result.get(0).getCategory(), "Категория должна совпадать");
    }

    @Test
    void testFindByCategoryNotFound() {
        StoreService service = new StoreService();
        service.addProduct(new Product(5L,"Футболkа", "Одежда", 8.99, "S", 1,"синий"));

        // Ищем категорию, которой точно нет
        List<Product> result = service.findByCategory("Электроника");

        // ПРОВЕРКА: теперь мы ОЖИДАЕМ, что список будет пустым
        assertTrue(result.isEmpty(), "Список должен быть пустым, если категория не найдена");
    }

    @Test
    void testDeleteProduct() {
        StoreService service = new StoreService();
        Product p = new Product(10L, "Джинсы", "Одежда", 25.99,"33", 1,"синий");
        service.addProduct(p);

        boolean isDeleted = service.deleteProductById(10L);

        assertTrue(isDeleted, "Метод должен вернуть true при успешном удалении");
        assertTrue(service.findByCategory("Одежда").isEmpty(), "Список должен быть пуст после удаления");
    }
}
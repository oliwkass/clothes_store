package org.example.service;

import org.example.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {
    // Используем List — это интерфейс, хороший тон в Java
    private List<Product> products = new ArrayList<>();

    // Метод для добавления товара
    public void addProduct(Product product) {
        products.add(product);
        System.out.println("Товар добавлен: " + product.getName());
    }

    // Метод для просмотра всех товаров
    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("Магазин пуст.");
        } else {
            products.forEach(System.out::println);
        }
    }

    public List<Product> findByCategory(String category) {
        List<Product> result = products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("Информация: По категории '" + category + "' товаров не найдено.");
        }

        return result;
    }

    public boolean deleteProductById(Long id) {
        boolean removed = products.removeIf(p -> p.getId().equals(id));
        if (removed) {
            System.out.println("✅ Товар с ID " + id + " успешно удален.");
        } else {
            System.out.println("❌ Товар с ID " + id + " не найден.");
        }
        return removed;
    }

    @jakarta.annotation.PostConstruct
    public void initData() {
        addProduct(new Product(1L, "Футболка", "Одежда", 1500.0, "L", 10, "Белый"));
        addProduct(new Product(2L, "Джинсы", "Одежда", 3500.0, "M", 5, "Синий"));
    }
}
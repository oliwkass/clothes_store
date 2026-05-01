package org.example.service;

import org.example.model.Product;

import java.util.ArrayList;
import java.util.List;

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

    public List<Product> findProductsByCategory(String category) {
        // 1. Создаем пустой список для результатов
        List<Product> foundProducts = new ArrayList<>();

        // 2. Цикл for-each: "для каждого Product p в списке products"
        for (Product p : products) {
            // 3. Сравниваем категории.
            // В Java строки сравниваются через .equals(), а не через ==
            if (p.getCategory().equalsIgnoreCase(category)) {
                foundProducts.add(p);
            }
        }

        // 4. Возвращаем результат
        return foundProducts;
    }
}
package org.example.service;

import org.example.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
}
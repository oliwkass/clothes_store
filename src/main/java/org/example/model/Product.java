package org.example.model;

public class Product {
    private Long id;          // Уникальный номер товара
    private String name;      // Название (например, "Черная футболка")
    private String category;  // Категория (Одежда, Обувь)
    private double price;     // Цена
    private String size;      // Размер (S, M, L, XL)
    private String color;      // Цвет товара
    private int stockQuantity; // Количество на складе

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }

    // Конструктор - нужен, чтобы создавать объект "одним махом"
    public Product(Long id, String name, String category, double price, String size, int stockQuantity, String color) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.size = size;
        this.stockQuantity = stockQuantity;
        this.color = color;
    }

    // Геттеры - чтобы другие части программы могли "читать" данные
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    // Сеттеры - чтобы можно было изменить цену или название
    public void setPrice(double price) { this.price = price; }
}
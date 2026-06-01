package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // 1. Помечаем как сущность базы данных
@Table(name = "products") // 2. Называем таблицу
@Getter

public class Product {

    @Id // 3. Указываем, что это первичный ключ (ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 4. База сама будет генерировать ID (1, 2, 3...)
    private Long id;          // Уникальный номер товара

    @NotBlank(message = "Название не может быть пустым")
    private String name;

    @Min(value = 0, message = "Цена не может быть меньше нуля")
    private Double price;

    @PositiveOrZero(message = "Количество на складе не может быть отрицательным")
    private Integer stockQuantity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id") // В базе появится колонка со ссылкой на ID категории
    @JsonIgnoreProperties
    private Category category;
    private String size;      // Размер (S, M, L, XL)
    private String color;      // Цвет товара

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }

    // Конструктор - нужен, чтобы создавать объект "одним махом"
    public Product(Long id, String name, Category category, double price, String size, int stockQuantity, String color) {
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
    public Double getPrice() {
        return price;
    }
    public Long getId(){return id;}

    public Category getCategory() {
        return this.category;
    }

    public void setId(Long id) {
        this.id = id;
    }
    // Сеттеры - чтобы можно было изменить цену или название
    public void setPrice(double price) { this.price = price; }


}
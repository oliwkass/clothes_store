package org.example;

import org.example.model.Product;
import org.example.service.StoreService;


public class Main {
    public static void main(String[] args) {

        StoreService StoreService = new StoreService();;

        Product hoodie = new Product(1L, "Худи с принтом", "Одежда", 49.99, "L", 1,"черная");
        Product jeans = new Product(2L, "Jeans Levis","Одежда", 39.99, "33", 1, "синий");
        Product TShirt = new Product(4L, "T-Shirt", "Polo", 9.99,"XXL", 1,"белый");

        StoreService.addProduct(hoodie);
        StoreService.addProduct(jeans);

        System.out.println("Категории");
        StoreService.findByCategory("Одежда").forEach(System.out::println);
        }
    }

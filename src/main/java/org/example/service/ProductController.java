package org.example.service;

import org.example.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ProductController {

    private final StoreService storeService;

    // Конструктор: Spring видит, что нужен StoreService, и сам его сюда подставит
    public ProductController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String category) {
        return storeService.findByCategory(category);
    }
}
package org.example.service;

import jakarta.validation.Valid;
import org.example.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/all")
    public List<Product> getAll() {
        return storeService.getAllProducts();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product) {
        storeService.addProduct(product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        boolean deleted = storeService.deleteProductById(id);

        if (deleted) {
            return ResponseEntity.ok("✅ Товар успешно удален");
        } else {
            return ResponseEntity.status(404).body("❌ Ошибка: Товар с ID " + id + " не существует");
        }
    }
}
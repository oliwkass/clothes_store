package org.example.service;

import jakarta.validation.Valid;
import org.example.dto.ProductDTO;
import org.example.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final StoreService storeService;
    private final ModelMapper modelMapper;

    // Конструктор: Spring видит, что нужен StoreService, и сам его сюда подставит
    public ProductController(StoreService storeService, ModelMapper modelMapper) {
        this.storeService = storeService;
        this.modelMapper = modelMapper;
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
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        // 1. Превращаем DTO в Entity для базы
        Product product = modelMapper.map(productDTO, Product.class);

        // 2. Сохраняем
        storeService.addProduct(product);

        // 3. Возвращаем DTO обратно клиенту
        return ResponseEntity.ok(modelMapper.map(product, ProductDTO.class));
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
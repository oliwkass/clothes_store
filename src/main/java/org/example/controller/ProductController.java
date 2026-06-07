package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.ProductDTO;
import org.example.model.Product;
import org.example.service.StoreService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final StoreService storeService;
    private final ModelMapper modelMapper;

    public ProductController(StoreService storeService, ModelMapper modelMapper) {
        this.storeService = storeService;
        this.modelMapper = modelMapper;
    }

    // 1. Объединенный и чистый поиск
    @GetMapping("/search")
    public List<ProductDTO> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        // Контроллер просто делегирует задачу сервису
        List<Product> results = storeService.getProductsByFilter(name, minPrice, maxPrice);

        return results.stream()
                .map(p -> modelMapper.map(p, ProductDTO.class))
                .collect(Collectors.toList());
    }

    // 2. Добавили @Valid для защиты при обновлении
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO) {

        Product productEntity = modelMapper.map(productDTO, Product.class);
        Product updatedProduct = storeService.updateProduct(id, productEntity);

        return ResponseEntity.ok(modelMapper.map(updatedProduct, ProductDTO.class));
    }

    // 3. Добавление с правильным статус-кодом 201 Created
    @PostMapping("/add")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        Product savedProduct = storeService.addProduct(product, productDTO.getCategory());

        return new ResponseEntity<>(modelMapper.map(savedProduct, ProductDTO.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        boolean deleted = storeService.deleteProductById(id);

        if (deleted) {
            return ResponseEntity.ok("✅ Товар успешно удален");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Ошибка: Товар с ID " + id + " не существует");
        }
    }
}
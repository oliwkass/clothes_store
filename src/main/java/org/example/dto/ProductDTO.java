package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    @NotBlank(message = "Product name cannot be empty")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;

    @PositiveOrZero(message = "Price cannot be negative")
    private Double price;

    @PositiveOrZero(message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    private String size;
    private String color;
    private String category;
}
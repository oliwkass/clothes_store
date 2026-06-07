package org.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class OrderItemRequest {
    // Геттеры и сеттеры (@Getter @Setter)

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

}

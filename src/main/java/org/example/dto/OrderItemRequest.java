package org.example.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class OrderItemRequest {
    // Геттеры и сеттеры (@Getter @Setter)
    private Long productId;
    private int quantity;

}

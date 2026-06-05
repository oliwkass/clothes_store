package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderRequest {
    // Геттеры и сеттеры
    private Long userId;
    private List<OrderItemRequest> items;

}
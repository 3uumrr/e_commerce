package com.example.e_commerce.dto.orderItem;

import com.example.e_commerce.model.Order;
import com.example.e_commerce.model.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long productId;
    private String productName;
    private String productBrand;
    private int quantity;
    private BigDecimal price;
}

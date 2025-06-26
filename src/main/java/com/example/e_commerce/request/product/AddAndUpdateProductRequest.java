package com.example.e_commerce.request.product;

import com.example.e_commerce.model.Category;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AddAndUpdateProductRequest {
    private String name;
    private String brand;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private Category category;
}

package com.example.e_commerce.dto.product;

import com.example.e_commerce.dto.image.ImageDto;
import com.example.e_commerce.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private Category category;
    private List<ImageDto> images;



}

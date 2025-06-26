package com.example.e_commerce.request.category;

import com.example.e_commerce.model.Category;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AddAndUpdateCategoryRequest {
    private String name;
}

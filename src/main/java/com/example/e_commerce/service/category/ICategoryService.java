package com.example.e_commerce.service.category;

import com.example.e_commerce.model.Category;
import com.example.e_commerce.request.category.AddAndUpdateCategoryRequest;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(AddAndUpdateCategoryRequest category);
    Category updateCategory(AddAndUpdateCategoryRequest category , Long id);
    void deleteCategoryById(Long id);
}

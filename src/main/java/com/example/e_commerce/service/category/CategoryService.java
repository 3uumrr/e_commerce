package com.example.e_commerce.service.category;

import com.example.e_commerce.exceptions.AlreadyExistsException;
import com.example.e_commerce.exceptions.CategoryNotFoundException;
import com.example.e_commerce.model.Category;
import com.example.e_commerce.repository.CategoryRepository;
import com.example.e_commerce.request.category.AddAndUpdateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new CategoryNotFoundException("Category not found!"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(AddAndUpdateCategoryRequest category) {
        Optional<Category> categoryInDb = categoryRepository.findByName(category.getName());
        if (categoryInDb.isPresent()){
            throw new AlreadyExistsException(category.getName() + " Already Exists!, you may updated it instead of!");
        }
        Category newCategory = Category.builder()
                .name(category.getName())
                .build();

        return categoryRepository.save(newCategory);
    }

    @Override
    public Category updateCategory(AddAndUpdateCategoryRequest newCategory , Long id) {

        Category oldCategory = getCategoryById(id); // if not found will throw exception
        oldCategory.setName(newCategory.getName());
        return categoryRepository.save(oldCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,() -> {
            throw new CategoryNotFoundException("Category not found!");
        });

    }
}

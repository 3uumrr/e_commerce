package com.example.e_commerce.service.product;

import com.example.e_commerce.dto.product.ProductDto;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.request.product.AddAndUpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);
    Product addProduct(AddAndUpdateProductRequest request);
    Product updateProduct(AddAndUpdateProductRequest product , Long id);
    void deleteProduct(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category , String brand);
    List<Product> getProductsByName(String ame);
    List<Product> getProductsByBrandAndName(String brand , String name);
    Long getProductCountByBrandAndName(String brand, String name);
    List<ProductDto> getConvertedProducts(List<Product> products);
    ProductDto convertToDto(Product product);
}

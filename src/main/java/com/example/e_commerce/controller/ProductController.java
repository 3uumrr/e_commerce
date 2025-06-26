package com.example.e_commerce.controller;

import com.example.e_commerce.dto.product.ProductDto;
import com.example.e_commerce.exceptions.AlreadyExistsException;
import com.example.e_commerce.exceptions.ResourceNotFoundException;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.request.product.AddAndUpdateProductRequest;
import com.example.e_commerce.response.ApiResponse;
import com.example.e_commerce.service.image.IImageService;
import com.example.e_commerce.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;
    private final IImageService imageService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Success" , convertedProducts));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("product/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
        try {
            Product product = productService.getProductById(id);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Success" , productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody AddAndUpdateProductRequest productRequest){
        try {
            Product product = productService.addProduct(productRequest);
            return ResponseEntity.ok(new ApiResponse("Add product success",product));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody AddAndUpdateProductRequest productRequest ,@PathVariable Long id){
        try {
            Product product = productService.updateProduct(productRequest,id);
            return ResponseEntity.ok(new ApiResponse("Success",product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse("Success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping("/by-name-and-brand")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand,@RequestParam String name){

        try {
            List<Product> products = productService.getProductsByBrandAndName(brand,name);
            if (products.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found ",null));

            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("success",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/by-category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category,@RequestParam String brand){

        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category,brand);

            if (products.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found ",null));

            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("success",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("/by-name")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam String name){

        try {
            List<Product> products = productService.getProductsByName(name);

            if (products.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found ",null));

            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);


            return ResponseEntity.ok(new ApiResponse("success",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand){

        try {
            List<Product> products = productService.getProductsByBrand(brand);

            if (products.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found ",null));

            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("success",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("/{category}/all")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category){

        try {
            List<Product> products = productService.getProductsByCategory(category);

            if (products.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found ",null));

            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("success",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/count/by-brand-and-name")
    public ResponseEntity<ApiResponse> getProductCountByBrandAndName(@RequestParam String brand, @RequestParam String name){

        try {
            Long productsCount = productService.getProductCountByBrandAndName(brand,name);
                return ResponseEntity.ok(new ApiResponse("success",productsCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

}

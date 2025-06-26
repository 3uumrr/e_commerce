package com.example.e_commerce.service.product;

import com.example.e_commerce.dto.image.ImageDto;
import com.example.e_commerce.dto.product.ProductDto;
import com.example.e_commerce.exceptions.AlreadyExistsException;
import com.example.e_commerce.exceptions.ResourceNotFoundException;
import com.example.e_commerce.model.Category;
import com.example.e_commerce.model.Image;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.repository.CategoryRepository;
import com.example.e_commerce.repository.ImageRepository;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.request.product.AddAndUpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor // For Constructor Injection
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public Product addProduct(AddAndUpdateProductRequest request) {
        // check if the category is found in the DB
        // If Yes, set it as the new product
        // If No, the save it as a new category
        // The set as the new product

        if (productExist(request.getName(), request.getBrand())){
            throw new AlreadyExistsException(request.getBrand() + " " + request.getName() + " already exist, you may update this product instead!");
        }

        Category category = categoryRepository.findByName(request.getCategory().getName())
                .orElseGet(() -> categoryRepository.save(
                        Category.builder()
                                .name(request.getCategory().getName())
                                .build()
                ));

        // Update the category inside the request to be the existing in the database
        request.setCategory(category);

        return productRepository.save(createProduct(request));
    }

    private boolean productExist(String name , String brand){

        return productRepository.existsByNameAndBrand(name,brand);

    }


    private Product createProduct(AddAndUpdateProductRequest request) { // For Mapping
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .brand(request.getBrand())
                .quantity(request.getQuantity())
                .category(request.getCategory())
                .build();
    }




    @Override
    public Product updateProduct(AddAndUpdateProductRequest request, Long id) {

        // Check if product was existed
        Product product = getProductById(id);

        Category category = categoryRepository.findByName(request.getCategory().getName())
                .orElseGet(() -> categoryRepository.save(
                        Category.builder()
                                .name(request.getCategory().getName())
                                .build()
                ));


        // Update the category inside the request to be the existing in the database
        request.setCategory(category);

        return productRepository.save(updateExistingProduct(request,product));
    }

    private Product updateExistingProduct(AddAndUpdateProductRequest request , Product product){ // For Mapping
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setBrand(request.getBrand());
        product.setQuantity(request.getQuantity());
        product.setCategory(request.getCategory());
        return product;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete ,
                () -> {throw new ResourceNotFoundException("Product not found!");});
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long getProductCountByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto = modelMapper.map(product,ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imagesDto = images.stream().map(image -> modelMapper
                .map(image,ImageDto.class))
                .toList();

        productDto.setImages(imagesDto);
        return productDto;
    }






}

package com.example.e_commerce.dto.cartItem;

import com.example.e_commerce.dto.cart.CartDto;
import com.example.e_commerce.dto.product.ProductDto;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private ProductDto product;

}

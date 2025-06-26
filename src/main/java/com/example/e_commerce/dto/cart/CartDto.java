package com.example.e_commerce.dto.cart;

import com.example.e_commerce.dto.cartItem.CartItemDto;
import lombok.Data;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data

public class CartDto {
    private Long id;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private Set<CartItemDto> cartItems;


}

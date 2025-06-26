package com.example.e_commerce.service.cart;

import com.example.e_commerce.model.Cart;
import com.example.e_commerce.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long cartId);
    void clearCart(Long cartId);
    BigDecimal getTotalPrice(Long cartId);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}

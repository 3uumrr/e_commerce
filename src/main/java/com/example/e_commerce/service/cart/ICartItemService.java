package com.example.e_commerce.service.cart;

import com.example.e_commerce.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId , Long productId , int quantity);
    void removeItemFromCart(Long cartId , Long productId );

    void updateItemQuantity(Long cartId , Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}

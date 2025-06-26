package com.example.e_commerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "cart")
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL , orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;



    public void addItem(CartItem item) {
        this.cartItems.add(item); // add item to cart
        item.setCart(this); // add relation between card and cardItem
        updateTotalAmount(); // update total amount for cart
    }

    public void removeItem(CartItem item) {
        this.cartItems.remove(item); // remove item from cart
        item.setCart(null); // remove relation between card and cardItem
        updateTotalAmount(); // update total amount for cart
    }

    public void updateTotalAmount() {
       this.totalAmount = cartItems.stream()
               .map(item -> {
                   BigDecimal unitPrice = item.getUnitPrice();
                   if(unitPrice == null)
                       return BigDecimal.ZERO;

                  return item.getUnitPrice().multiply(new BigDecimal(item.getQuantity()));

               }).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

}

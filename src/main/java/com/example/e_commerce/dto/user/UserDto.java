package com.example.e_commerce.dto.user;

import com.example.e_commerce.dto.cart.CartDto;
import com.example.e_commerce.dto.order.OrderDto;
import lombok.Data;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private CartDto cart;
    private List<OrderDto> orders;
}

package com.example.e_commerce.dto.order;

import com.example.e_commerce.dto.orderItem.OrderItemDto;
import com.example.e_commerce.model.OrderItem;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private LocalDate orderDate;
    private BigDecimal orderTotalAmount;
    private Long userId;
    private String orderStatus;
    private List<OrderItemDto> orderItems;
}

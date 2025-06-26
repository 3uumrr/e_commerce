package com.example.e_commerce.service.order;

import com.example.e_commerce.dto.order.OrderDto;
import com.example.e_commerce.enums.OrderStatus;
import com.example.e_commerce.model.Order;
import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
    OrderDto getOrderDto(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
    OrderDto updateOrder(Long orderId, OrderStatus status);
    void cancelOrder(Long orderId);
}

package com.example.e_commerce.service.order;

import com.example.e_commerce.dto.order.OrderDto;
import com.example.e_commerce.dto.orderItem.OrderItemDto;
import com.example.e_commerce.enums.OrderStatus;
import com.example.e_commerce.exceptions.ResourceNotFoundException;
import com.example.e_commerce.model.*;
import com.example.e_commerce.repository.OrderRepository;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.UserRepository;
import com.example.e_commerce.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;
    private final UserRepository userRepository;

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public OrderDto getOrderDto(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("user not found"));

        return orderRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    @Transactional
    public Order placeOrder(Long userId) {

        Cart cart = cartService.getCartByUserId(userId);

        Order order = createOrder(cart);

        order = orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return order;
    }


    private Order createOrder(Cart cart){

        Order order = new Order();
        order.setUser(cart.getUser());

        List<OrderItem> orderItems = createOrderItems(order,cart);

        order.setOrderItems(new HashSet<>(orderItems));
        order.setOrderTotalAmount(calculateTotalAmount(orderItems));
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);

        return order;
    }


    // To convert items from cart to order
    private List<OrderItem> createOrderItems(Order order , Cart cart){
        return cart.getCartItems().stream()
                .map(item -> {
                    Product product = item.getProduct();

                    if (item.getQuantity() > product.getQuantity()) {
                        throw new RuntimeException("Not enough stock for product: " + product.getName());
                    }

                    product.setQuantity(product.getQuantity() - item.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(product,order,item.getQuantity(),item.getUnitPrice());
                }).toList();

    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems){

        return orderItems.stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);

    }

    @Override
    public OrderDto updateOrder(Long orderId, OrderStatus status) {
        Order order = getOrder(orderId);
        order.setOrderStatus(status);
        return convertToDto(orderRepository.save(order));
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = getOrder(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    private OrderDto convertToDto(Order order){
        OrderDto orderDto =  new OrderDto();

        orderDto.setId(order.getId());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setOrderTotalAmount(order.getOrderTotalAmount());
        orderDto.setOrderStatus(order.getOrderStatus().toString());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setOrderItems(order.getOrderItems().stream().map(
                orderItem -> {
                    OrderItemDto orderItemDto = new OrderItemDto();
                    orderItemDto.setProductId(orderItem.getProduct().getId());
                    orderItemDto.setProductName(orderItem.getProduct().getName());
                    orderItemDto.setQuantity(orderItem.getQuantity());
                    orderItemDto.setPrice(orderItem.getPrice());
                    orderItemDto.setProductBrand(orderItem.getProduct().getBrand());
                    return orderItemDto;}).toList());

        return orderDto;
    }


}

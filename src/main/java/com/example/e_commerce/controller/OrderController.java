package com.example.e_commerce.controller;

import com.example.e_commerce.dto.order.OrderDto;
import com.example.e_commerce.dto.orderItem.OrderItemDto;
import com.example.e_commerce.enums.OrderStatus;
import com.example.e_commerce.exceptions.ResourceNotFoundException;
import com.example.e_commerce.model.Order;
import com.example.e_commerce.response.ApiResponse;
import com.example.e_commerce.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try {
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.getOrderDto(order.getId());
            return ResponseEntity.ok(new ApiResponse("Item Order Success" , orderDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long id){
        try {
            OrderDto order = orderService.getOrderDto(id);
            return ResponseEntity.ok(new ApiResponse("Success" , order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getUserOrders(@RequestParam Long userId){
        try {
            List<OrderDto> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Success" , orders));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("User not found",null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/order/{id}/update")
    public ResponseEntity<ApiResponse> updateOrderStatue(@PathVariable Long id,@RequestParam String status){
        try {
            Order order = orderService.getOrder(id);
            OrderDto orderDto = orderService.updateOrder(id,OrderStatus.valueOf(status));

            return ResponseEntity.ok(new ApiResponse("Update Success" , orderDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("User not found",null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/order/{id}/cancel")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Long orderId){
        try {
            Order order = orderService.getOrder(orderId);
            orderService.cancelOrder(orderId);

            return ResponseEntity.ok(new ApiResponse("Cancel Success" , null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("User not found",null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }


}

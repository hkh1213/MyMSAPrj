package com.example.orderservice.service;

import com.example.orderservice.DTO.OrderDTO;
import com.example.orderservice.jpa.OrderEntity;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDetails);

    OrderDTO getOrdersByOrderId(String orderId);

    Iterable<OrderEntity> getOrdersByUserId(String userId);
}

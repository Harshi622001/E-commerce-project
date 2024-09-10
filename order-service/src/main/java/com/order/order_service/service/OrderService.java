package com.order.order_service.service;

import com.order.order_service.entity.Order;
import com.order.order_service.entity.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Order order);

    Optional<Order> getOrderById(Long id);

    List<Order> getAllOrders();

    Order updateOrder(Long id);

    List<OrderDto> getAllOrdersOfUser(String userId);
}

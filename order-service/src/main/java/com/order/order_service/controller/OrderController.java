package com.order.order_service.controller;

import com.order.order_service.entity.Order;
import com.order.order_service.entity.dto.OrderDto;
import com.order.order_service.service.OrderService;
import org.aspectj.weaver.ICrossReferenceHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public Order createOrder(@RequestBody Order order)
    {
        return orderService.createOrder(order);
    }

    @GetMapping("getOrderById/{id}")
    public Order getOrderById(@PathVariable Long id) {

        return orderService.getOrderById(id).orElse(null);
    }

    @GetMapping("/getAllOrders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PatchMapping("/update/{id}")
    public String updateOrder(@PathVariable Long id) {

        return orderService.updateOrder(id).toString();
    }

    @GetMapping("/{userId}")
    public List<OrderDto> getAllOrdersOfUser(@PathVariable String userId) {
        return orderService.getAllOrdersOfUser(userId);
    }

}
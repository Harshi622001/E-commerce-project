package com.order.order_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.order_service.entity.Order;
import com.order.order_service.entity.OrderUpdateMessage;
import com.order.order_service.entity.ShippingMessage;
import com.order.order_service.entity.dto.OrderDto;
import com.order.order_service.repository.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired(required = true)
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "shipping_topic";

    @Override
    public Order createOrder(Order order) {
        order.setStatus("New");
        Order createdOrder = orderRepo.save(order);

        // create shipping message for shipping_topic
        ShippingMessage message = new ShippingMessage();
        message.setOrderId(createdOrder.getId().toString());
        message.setUserId(createdOrder.getUserId());

        ObjectMapper objectMapper = new ObjectMapper();
        //converting into json string
        String messageString;
        try {
            messageString = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting message to JSON string", e);
        }

        kafkaTemplate.send(TOPIC, messageString);

        return createdOrder;
    }

    @Override
    public Optional<Order> getOrderById(Long id) {

        return orderRepo.findById(id);
    }

    @Override
    public List<Order> getAllOrders() {

        return orderRepo.findAll();
    }

    @Override
    public Order updateOrder(Long id) {
        Order order = orderRepo.findById(id).get();
        order.setStatus("Delivered");
        return orderRepo.save(order);
    }

    @Override
    public List<OrderDto> getAllOrdersOfUser(String userId) {
        List<Order> orders = orderRepo.findAllOrdersByUserId(userId);
        return orders.stream()
                .map(order -> {
                    OrderDto orderDto = new OrderDto();
                    orderDto.setQuantity(order.getQuantity());
                    orderDto.setStatus(order.getStatus());
                    orderDto.setProductId(order.getProductId());
                    return orderDto;
                })
                .collect(Collectors.toList());
    }

    @KafkaListener(topics = "shipping_topic1", groupId = "group_id1")
    public void consumeOrderUpdate(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderUpdateMessage orderUpdateMessage;
        try {
            orderUpdateMessage = objectMapper.readValue(message, OrderUpdateMessage.class);
            System.out.println("Order ID: " + orderUpdateMessage.getOrderId());
            System.out.println("Status: " + orderUpdateMessage.getStatus());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON message", e);
        }
        Long orderId = orderUpdateMessage.getOrderId();
        Optional<Order> optionalOrder = orderRepo.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(orderUpdateMessage.getStatus());
            orderRepo.save(order);
        } else {
            log.warn("Order with ID {} not found", orderId);
        }
    }
}
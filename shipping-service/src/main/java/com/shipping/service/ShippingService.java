package com.shipping.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipping.modle.OrderUpdateMessage;
import com.shipping.modle.Shipping;
import com.shipping.repository.ShippingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //consume data from shipping topic
    @KafkaListener(topics = "shipping_topic", groupId = "group_id")
    public void consume(ShippingMessage message) {
        System.out.println(message);
        Shipping shipping = new Shipping();
        shipping.setOrderID(message.getOrderId());
        shipping.setUserID(message.getUserId());
        shipping.setStatus("In Process");
        log.info("shipping message {}", message);
        shippingRepository.save(shipping);
        scheduleStatusUpdate(shipping);
        updateOrder(message.getOrderId());
    }

    private void scheduleStatusUpdate(Shipping shipping) {
        shipping.setStatus("Completed");
        shippingRepository.save(shipping);
        scheduleStatus(shipping);
    }
    @Async
    private void scheduleStatus(Shipping shipping) {
        try {
            TimeUnit.MINUTES.sleep(2);
            updateOrder(shipping.getOrderID());
            sendOrderUpdate(shipping.getOrderID());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
    public void updateOrder(Long id) {
        sendOrderUpdate(id);
    }

    private void sendOrderUpdate(Long orderId) {
        String topic = "shipping_topic1";
        OrderUpdateMessage orderUpdateMessage = new OrderUpdateMessage(String.valueOf(orderId), "Delivered");

        ObjectMapper objectMapper = new ObjectMapper();
        String messageString;
        try {
            messageString = objectMapper.writeValueAsString(orderUpdateMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting message to JSON string", e);
        }

        kafkaTemplate.send(topic, messageString);
    }
}
